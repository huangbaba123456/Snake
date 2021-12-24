import {Vector} from './Vector.js';
import {animate} from './animate_function.js';

export class Snake{
    static size = 30;
    static distance = 20;
    static attach_range = 100;
    static attach_able = 10; // 使食物沿与蛇头连线方向偏移的距离
    constructor(px, py, len, {rate=5, dir=new Vector(1, 0), style_id=1}={}){
        this.px = px;
        this.py = py;
        this.len = len;
        this.rate = rate;
        this.dir = dir;
        this.init();
        this.setStyle(style_id);
    }
    init(){
        this.isPause = false;
        this.isAttach = false;
        this.isLaser = false;
        this.laser_width = 10;

        let container = document.getElementById('container');
        this.body = [];
        this.body_pos = [];
        for (let i=0; i<this.len; i++){
            let div = document.createElement('div');
            div.style.position = 'absolute';
            div.classList.add('snake_body');
            div.style.left = `${this.px - Snake.size/2 - i*this.dir.x*Snake.distance}px`;
            div.style.top = `${this.py - Snake.size/2 - i*this.dir.y*Snake.distance}px`;
            div.style.width = `${Snake.size}px`;
            div.style.height = `${Snake.size}px`;
            div.style.zIndex = `${-i}`;
            container.appendChild(div);
            this.body.push(div);
            this.body_pos.push([parseFloat(div.style.left), parseFloat(div.style.top)]);
        }
        this.on('speedUp', speedUp);
        this.on('laser', laser);
        this.on('attach', attach);

        /******提交事件************************* */
        this.on('complete', function(){
            // this.len
            console.log('current score:' + this.len);
        });
        //**************** */
    }

    setStyle(style_id=1){
        this.style_id = style_id;
        if (style_id == 1){
            // 滑稽
            this.body[0].style.background = "url('./image/hj.png') no-repeat";
            this.body[0].style.backgroundSize = "100% 100%";
            for (let i=1; i<this.len; i++){
                this.body[i].style.background = "rgba(253, 229, 139, 1)";
            }
        } else if (style_id == 2){
            // 大嘴巴
            this.body[0].style.background = "url('./image/dzb.png') no-repeat";
            this.body[0].style.backgroundSize = "100% 100%";
            for (let i=1; i<this.len; i++){
                this.body[i].style.background = "rgba(37,255,80, 1)";
            }
        }
    }
    eat(kind){
        if (kind == 1){
            let container = document.getElementById('container');
            let div = document.createElement('div');
            div.style.position = 'absolute';
            div.classList.add('snake_body'); 
            let tail = this.body_pos[this.len-1];
            let vector = null;
            if (this.len == 1){
                vector = this.dir.mul(Snake.distance).mul(-1);
            } else {
                let tail_1 = this.body_pos[this.len-2];
                vector = new Vector(tail_1[0]-tail[0], tail_1[1]-tail[1]).mul(-1);
            }
            let new_pos = [tail[0]+vector.x, tail[1]+vector.y];
            div.style.left = `${new_pos[0]}px`;
            div.style.top = `${new_pos[1]}px`;
            div.style.width = `${Snake.size}px`;
            div.style.height = `${Snake.size}px`;
            div.style.zIndex = `${-this.len}`;
            container.appendChild(div);
            this.body.push(div);
            this.body_pos.push(new_pos);
            this.len++;
            this.setStyle(this.style_id);
        } else if(kind == 2){
            this.eat(1);
            this.eat(1);
        }
    }

    change_pos(i, newX, newY){
        this.body_pos[i][0] = newX;
        this.body_pos[i][1] = newY;
        this.body[i].left = `${newX}px`;
        this.body[i].top = `${newY}px`;
    }
}
export function limit_frequence(f, frequence){
    let pre = -1;
    return function(...args){
        let delta = Date.now() - pre;
        if (delta >= frequence){
            f.apply(this, args);
            pre = Date.now();
        }
    }
}

function showTip(skill, ms){
    let skill_rect = skill.getBoundingClientRect();
    let tip = document.createElement('span');
    document.body.append(tip);
    tip.style.position = 'absolute';
    tip.style.left = `${skill_rect.left+skill_rect.width}px`;
    tip.style.top = `${skill_rect.top+skill_rect.height/3}px`;
    tip.style.fontSize = '25px';
    tip.style.color = 'white';
    skill.style.background = 'rgba(128, 128, 128, 0.9)';
    animate({
        duration: ms,
        timing: x => x,
        draw(progress){
            skill.style.background = `rgba(128, 128, 128, ${0.9 - 0.8*progress})`;
            let rest = (''+(1-progress)*ms/1000).substr(0, 4);
            tip.innerHTML = rest+'s';
            if (progress == 1){
                tip.remove();
            }
        }
    })
}

// 加速技能 冷却2000ms
function speedUp(speed_delta=5){
    console.log('speedUp!');
    if (this.len == 1) return;
    this.rate += speed_delta;
    let div = this.body.pop();
    div.remove();
    this.body_pos.pop();
    this.len--;
    setTimeout(() => {
        this.rate -= speed_delta;
    }, 1000);

    let skill = document.querySelector('#speedUp div');
    showTip(skill, 2000);
}
speedUp = limit_frequence(speedUp, 2000);


// 激光技能 冷却5000ms
function laser(container, {holdTime=2000}={}){
    console.log('laser!');
    let laser_width = this.laser_width;
    let container_rect = container.getBoundingClientRect();
    let width = container_rect.width;
    let height = container_rect.height;
    let laser_div = document.createElement('div');
    laser_div.classList.add('laser');
    container.append(laser_div);

    let snake = this;
    snake.isLaser = true;
    let start = performance.now();
    requestAnimationFrame(function loop(time){
        /*********激光持续 2s****** */
        if ((time - start) >= holdTime) {
            laser_div.remove();
            snake.isLaser = false;
            return;
        }
        /************************ */
        let head_pos = snake.body_pos[0];
        let cx = head_pos[0] + Snake.size/2;
        let cy = head_pos[1] + Snake.size/2;
        let limit_alpha1 = Math.atan(cy / (width - cx)) * 180/Math.PI; // 右上
        let limit_alpha2 = Math.atan((height - cy) / (width - cx)) * 180/Math.PI; // 右下
        let limit_alpha3 = Math.atan((height - cy) / cx) * 180/Math.PI; // 左下
        let limit_alpha4 = Math.atan(cy / cx) * 180/Math.PI; // 左上
        let alpha = Math.acos(snake.dir.dot(new Vector(1, 0))) * 180/Math.PI;
        let laser_len;
        if ((snake.dir.y<0 && alpha<limit_alpha1) || (snake.dir.y>0 && alpha<limit_alpha2)){
            laser_len = (width - cx) / snake.dir.mul(1000).x * 1000;
            snake.laser_end_pos = [width, cy+laser_len*snake.dir.y];
        } else if ((snake.dir.y<0 && alpha>=(180-limit_alpha4)) || (snake.dir.y>0 && alpha>=(180-limit_alpha3))){
            laser_len = (-cx) / snake.dir.mul(1000).x * 1000;
            snake.laser_end_pos = [0, cy+laser_len*snake.dir.y];
        } else if (snake.dir.y<0 && (alpha>=limit_alpha1 && alpha<(180-limit_alpha4))){
            laser_len = (-cy) / snake.dir.mul(1000).y * 1000;
            snake.laser_end_pos = [cx+laser_len*snake.dir.x, 0];
        } else {
            laser_len = (height-cy) / snake.dir.mul(1000).y * 1000;
            snake.laser_end_pos = [cx+laser_len*snake.dir.x, height];
        }
        if (snake.dir.y < 0) alpha *= -1;
        laser_div.style.width = `${laser_len}px`;
        laser_div.style.height = `${laser_width}px`;
        laser_div.style.transform = `rotate(${alpha}deg)`;
        laser_div.style.left = `${cx - laser_len/2*(1-Math.cos(alpha * Math.PI/180))}px`;
        laser_div.style.top = `${cy - laser_width/2 + laser_len/2*Math.sin(alpha * Math.PI/180)}px`;

        requestAnimationFrame(loop)
    })
    
    let skill = document.querySelector('#laser div');
    showTip(skill, 5000);
}
laser = limit_frequence(laser, 5000);


// 吸附技能 冷却10000ms
function attach(container, {holdTime=8000}={}){
    console.log('attach!');
    let snake = this;
    snake.isAttach = true;
    let r = Snake.attach_range;
    let attach_pan = document.createElement('div');
    attach_pan.style.width = `${r*2}px`;
    attach_pan.style.height = `${r*2}px`;
    attach_pan.classList.add('attach');
    container.append(attach_pan);

    let start = performance.now();
    requestAnimationFrame(function loop(time){
        /*********吸附持续 8s****** */
        if ((time - start) >= holdTime) {
            attach_pan.remove();
            snake.isAttach = false;
            return;
        }
        /************************ */
        let head_pos = snake.body_pos[0];
        let cx = head_pos[0] + Snake.size/2;
        let cy = head_pos[1] + Snake.size/2;
        attach_pan.style.left = `${cx - r}px`;
        attach_pan.style.top = `${cy - r}px`;

        requestAnimationFrame(loop);
    })
    
    let skill = document.querySelector('#attach div');
    showTip(skill, 10000);
}
attach = limit_frequence(attach, 10000);

