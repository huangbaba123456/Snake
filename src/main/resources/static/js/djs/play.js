import {Snake, limit_frequence} from './Snake.js';
import {Vector} from './Vector.js';
import {eventMixin} from './eventMixin.js';
 //container
let container = document.getElementById('container');
container.style.width = '80%';
container.style.height = '85%';
let container_rect = container.getBoundingClientRect();
let pos_left = window.innerWidth/2 - container_rect.width/2;
container.style.left = `${pos_left}px`;
container_rect = container.getBoundingClientRect();
// Block
class Block{

}


//Food /********************************************************************************************** */
class Food{
    constructor(kind=1){
        this.kind = kind;
        this.rate = 10 + Math.random()*10; // 10~20
        
        this.cx = Snake.size/2 + Math.random()*(container_rect.width-Snake.size);
        this.cy = Snake.size/2 + Math.random()*(container_rect.height-Snake.size);
        this.init();
    }

    init(){
        this.isPause = false;
        this.isEated = false;

        let div = document.createElement('div');
        div.style.width = `${Snake.size}px`;
        div.style.height = `${Snake.size}px`;
        div.style.left = `${this.cx-Snake.size/2}px`;
        div.style.top = `${this.cy-Snake.size/2}px`;
        div.classList.add('food_body');
        container.appendChild(div);
        this.body = div;

        this.setStyle();
        let left_value = Math.random();
        this.dir = new Vector(left_value, Math.sqrt(1-left_value**2));
    }

    setStyle(img = false){
        if (this.kind == 1){
            if (img){
                this.body.style.background = `url('***img_url***') 100% 100% no-repeat`;
            }else{
                this.body.style.background = 'radial-gradient(rgba(226, 59, 137, 0.6), rgba(86, 152, 228, 0.6))' ;
            }
        } else if(this.kind == 2){
            if (img){
                this.body.style.background = `url('***img_url***') 100% 100% no-repeat`;
            }else{
                this.body.style.background = 'radial-gradient(#29eac4, #4284db)' ;
            }
        } else if(this.kind == 3){
            if (img){
                this.body.style.background = `url('***img_url***') 100% 100% no-repeat`;
            }else{
                this.body.style.background = 'linear-gradient(to bottom, #cbb4d4, #20002c)' ;
            }
        }
    }

    next(){
        this.rate = 10 + Math.random()*10; // 10~20
        let left_value = Math.random();
        this.dir = new Vector(left_value, Math.sqrt(1-left_value**2));
        this.change_pos(Snake.size/2 + Math.random()*(container_rect.width-Snake.size), Snake.size/2 + Math.random()*(container_rect.height-Snake.size))
    }

    change_pos(cx, cy){
        this.cx = cx;
        this.cy = cy;
        this.body.style.left = `${this.cx-Snake.size/2}px`;
        this.body.style.top = `${this.cy-Snake.size/2}px`;
    }
}
Object.assign(Food.prototype, eventMixin);

let foods = [new Food(1), new Food(2), new Food(1), new Food(1), new Food(1), new Food(1), new Food(1), new Food(3)];
// 每2000ms生成一个炸弹
setInterval(() => {
    let new_bomb = new Food(3);
    food_run(new_bomb);
    foods.push(new_bomb);
}, 2000);
// 每3000ms随机生成一个kind=1或2的食物
setInterval(() => {
    let kind = Math.floor(1 + Math.random()*2);
    if (kind == 3) kind = 2;
    let new_food = new Food(kind);
    food_run(new_food);
    foods.push(new_food);
}, 3000)
function food_run(food){
    requestAnimationFrame(function draw(){
        if (food.isPause) return;
        if (food.isEated) return;

        food.cx += food.dir.x * food.rate;
        food.cy += food.dir.y * food.rate;
        food.body.style.left = `${food.cx-Snake.size/2}px`;
        food.body.style.top = `${food.cy-Snake.size/2}px`;

        if ((food.cx + Snake.size/2) >= container_rect.width || (food.cx - Snake.size/2) <= 0){
            if ((food.cx + Snake.size/2) >= container_rect.width){
                food.cx = container_rect.width - Snake.size/2;
            } else {
                food.cx = 0 + Snake.size/2;
            }
            food.body.style.left = `${food.cx-Snake.size/2}px`;
            food.dir = new Vector(-food.dir.x, food.dir.y);
        }
        if ((food.cy + Snake.size/2) >= container_rect.height || (food.cy - Snake.size/2) <= 0){
            if ((food.cy + Snake.size/2) >= container_rect.height){
                food.cy = container_rect.height - Snake.size/2;
            } else {
                food.cy = 0 + Snake.size/2;
            }
            food.body.style.top = `${food.cy-Snake.size/2}px`;
            food.dir = new Vector(food.dir.x, -food.dir.y);
        }

        let other_foods = foods.filter(f => food !== f);
        let crash_foods = other_foods.filter(f => {
            return new Vector(f.cx-food.cx, f.cy-food.cy).len <= Snake.size;
        });
        crash_foods.forEach(f => {
            let distance_vector = new Vector(f.cx-food.cx, f.cy-food.cy);
            let rest_k = 1 -distance_vector.len / Snake.size;
            let food_change_vector = distance_vector.mul(-1).mul(rest_k/2);
            let f_change_vector = distance_vector.mul(rest_k/2);
            food.change_pos(food.cx+food_change_vector.x, food.cy+food_change_vector.y);
            f.change_pos(f.cx+f_change_vector.x, f.cy+f_change_vector.y);

            let temp_v = f.dir.mul(f.rate);
            f.dir = food.dir.mul(food.rate).div(f.rate);
            food.dir = temp_v.div(food.rate);
        });

        requestAnimationFrame(draw);
    });
}
foods.forEach(food => food_run(food));
/**************************************************************************************** */

// Snake
Object.assign(Snake.prototype, eventMixin);

let s1 = new Snake(100, 100, 5, {style_id: 2});
window.onkeydown = function(e){
    if (e.code == 'KeyQ'){
        s1.trigger('speedUp');
        score_tip.innerHTML = `${s1.len}`;
    } else if(e.code == 'KeyW'){
        s1.trigger('laser', container);
        score_tip.innerHTML = `${s1.len}`;
    } else if (e.code == 'Space'){
        if (s1.isPause){
            start.click();
        } else {
            pause.click();
        }
    } else if (e.code == 'KeyE'){
        s1.trigger('attach', container)
    }
};
function snake_run(snake){
    requestAnimationFrame(function draw(){
        if (snake.isPause) return;
        
        let first_vec = snake.dir.mul(snake.rate);
        let first_new_pos = [snake.body_pos[0][0]+first_vec.x, snake.body_pos[0][1]+first_vec.y];
        snake.body_pos[0] = first_new_pos;
        snake.body[0].style.left = `${first_new_pos[0]}px`;
        snake.body[0].style.top = `${first_new_pos[1]}px`;

        // 转头
        if (snake.style_id!=1){
            let dot_val = new Vector(snake.dir.x, -snake.dir.y).dot(new Vector(0, 1));
            let alpha = Math.acos(dot_val) * 180/Math.PI;
            if (snake.dir.x < 0) alpha *= -1;
            snake.body[0].style.transform = `rotate(${alpha}deg)`;
        }
        for (let i=1; i<snake.len; i++){
            let pos_i = snake.body_pos[i];
            let pos_i_1 = snake.body_pos[i-1];
            let vector = new Vector(pos_i_1[0]-pos_i[0], pos_i_1[1]-pos_i[1]);
            let l = 1 - Snake.distance / vector.len;
            vector = vector.mul(l);
            let new_pos = [pos_i[0]+vector.x, pos_i[1]+vector.y];
            snake.body_pos[i] = new_pos;
            snake.body[i].style.left = `${new_pos[0]}px`;
            snake.body[i].style.top = `${new_pos[1]}px`;

            // 转身体
            if (snake.style_id!=1){
                let vector_unit = vector.unit();
                let dot_val = new Vector(vector_unit.x, -vector_unit.y).dot(new Vector(0, 1));
                let alpha = Math.acos(dot_val) * 180/Math.PI;
                if (vector_unit.x < 0) alpha *= -1;
                snake.body[i].style.transform = `rotate(${alpha}deg)`;
            }
        }
        // 检测是否撞墙
        if ((first_new_pos[0]+Snake.size) >= container_rect.width ||
            (first_new_pos[1]+Snake.size) >= container_rect.height ||
            (first_new_pos[0]) <= 0 ||
            (first_new_pos[1]) <= 0
        ){
            // 撞墙后触发的事件
            snake.trigger('complete');
            return;
        }
        // 检测是否处在吸附状态
        if (snake.isAttach){
            let head_pos = snake.body_pos[0];
            let cx = head_pos[0]+Snake.size/2;
            let cy = head_pos[1]+Snake.size/2;

            let in_attach_range_foods = foods.filter(food => new Vector(food.cx-cx, food.cy-cy).len <= Snake.attach_range);
            let in_attach_range_foods_no_bomb = in_attach_range_foods.filter(food => food.kind != 3);
            in_attach_range_foods_no_bomb.forEach(food => {
                let move_vector = new Vector(cx-food.cx, cy-food.cy).unit().mul(Snake.attach_able);
                food.change_pos(food.cx+move_vector.x, food.cy+move_vector.y);
            })
        }
        // 检测是否处在发射激光状态
        if (snake.isLaser){
            if (snake.laser_end_pos){
                let head_pos = snake.body_pos[0];
                let cx = head_pos[0]+Snake.size/2;
                let cy = head_pos[1]+Snake.size/2;

                let laser_end_pos_x = snake.laser_end_pos[0];
                let laser_end_pos_y = snake.laser_end_pos[1];

                let k = (laser_end_pos_y-cy) / (laser_end_pos_x-cx);
                for (let i=0; i<foods.length; i++){
                    let d = Math.abs(foods[i].cy - k*(foods[i].cx-cx) - cy) / Math.sqrt(1+k**2);
                    let laser_vector_unit = new Vector(laser_end_pos_x-cx, laser_end_pos_y-cy).unit();
                    let head_to_food_vector_unit = new Vector(foods[i].cx-cx, foods[i].cy-cy).unit();
                    let alpha = Math.acos(head_to_food_vector_unit.dot(laser_vector_unit)) * 180/Math.PI;
                    if (alpha<=90 && d<=Snake.size/2){
                        if (foods[i].kind == 3){
                            foods[i].body.remove();
                            foods.splice(i--, 1);
                        } else if(foods[i].kind == 1 || foods[i].kind == 2){
                            snake.eat(foods[i].kind);
                            foods[i].isEated = true;
                            foods[i].body.remove();
                            foods.splice(i--, 1);
                            score_tip.innerHTML = `${snake.len}`;
                        }
                    }
                }
            }
        }

        // 检测是否吃到食物
        for (let i=0; i<foods.length; i++){
            let head_to_food = new Vector(foods[i].cx-(first_new_pos[0]+Snake.size/2), foods[i].cy-(first_new_pos[1]+Snake.size/2)).len;
            if (head_to_food < Snake.size/2){
                if (foods[i].kind == 3){
                    snake.trigger('complete');
                    return;
                } else if(foods[i].kind == 1 || foods[i].kind == 2){
                    snake.eat(foods[i].kind);
                    foods[i].isEated = true;
                    foods[i].body.remove();
                    foods.splice(i--, 1);
                    score_tip.innerHTML = `${snake.len}`;
                }
            }
        }

        requestAnimationFrame(draw);
    })
}
snake_run(s1);


 //wheel and wheel_pan
let wheel = document.getElementById('wheel');
let wheel_rect = wheel.getBoundingClientRect();
let wheel_pan = document.getElementById('wheel_pan');
let wheel_pan_rect = wheel_pan.getBoundingClientRect();
wheel.ondragstart = function(e){
    e.preventDefault();
}
wheel_pan.ondragstart = function(e){
    e.preventDefault();
}

wheel.style.left = `${container_rect.left}px`;
wheel.style.top = `${container_rect.top+container_rect.height-wheel_rect.height}px`;

wheel_rect = wheel.getBoundingClientRect();
wheel_pan.style.left = `${wheel_rect.left+wheel_rect.width-wheel_pan_rect.width/2}px`;
wheel_pan.style.top = `${wheel_rect.top+wheel_rect.height/2-wheel_pan_rect.height/2}px`;

let wheel_center_x = wheel_rect.left + wheel_rect.width/2;
let wheel_center_y = wheel_rect.top + wheel_rect.height/2;
function control(e_down){
    wheel_pan.setPointerCapture(e_down.pointerId);

    function move(e){
        let x = e.clientX;
        let y = e.clientY;
        let vector = new Vector(x-wheel_center_x, y-wheel_center_y);
        let vector_unit = vector.unit();
        let dot_val = new Vector(vector_unit.x, -vector_unit.y).dot(new Vector(0, 1));
        let alpha = Math.acos(dot_val) * 180/Math.PI;
        if (vector.x < 0) alpha *= -1;
        wheel.style.transform = `rotate(${alpha}deg)`;
        s1.dir = vector_unit;
        let vector_len = Math.min(vector.len, wheel_rect.width/2)
        let new_x = wheel_center_x + vector_unit.x*vector_len;
        let new_y = wheel_center_y + vector_unit.y*vector_len;
        wheel_pan.style.left = `${new_x - wheel_pan_rect.width/2}px`;
        wheel_pan.style.top = `${new_y - wheel_pan_rect.height/2}px`;
    }

    wheel_pan.onpointermove = move

    wheel_pan.onpointerup = function(e_up){
        wheel_pan.onpointermove = null;
        wheel_pan.onpointerup = null;

        move(e_up);
    }
}
container.onpointerdown = control;
wheel.onpointerdown = control;
wheel_pan.onpointerdown = control;


// snake_skill
let snake_skills = document.querySelectorAll('.snake_skill');
snake_skills[0].style.top = `${container_rect.top}px`;
let gap = 10;
for (let i=1; i<snake_skills.length; i++){
    snake_skills[i].style.top = `${container_rect.top + i*100 + i*gap}px`;
}
snake_skills.forEach(skill => {
    skill.style.left = `${container_rect.left-gap-100}px`;
})
document.getElementById('speedUp').onclick = function(){
    s1.trigger('speedUp');
    score_tip.innerHTML = `${s1.len}`;
}
document.getElementById('laser').onclick = function(){
    s1.trigger('laser', container);
    score_tip.innerHTML = `${s1.len}`;
}
document.getElementById('attach').onclick = function(){
    s1.trigger('attach', container);
    score_tip.innerHTML = `${s1.len}`;
}


// score
let score = document.getElementById('score');
let score_rect = score.getBoundingClientRect();
score.style.left = `${container_rect.left+container_rect.width-score_rect.width}px`;
score.style.top = `${container_rect.top-score_rect.height}px`;
let score_tip = document.querySelector('#score span');
score_tip.innerHTML = `${s1.len}`;


// pause
let pause = document.getElementById('pause');
let start = document.getElementById('start');
let pause_rect = pause.getBoundingClientRect();
pause.style.left = `${container_rect.left}px`;
pause.style.top = `${container_rect.top-pause_rect.height}px`;
pause_rect = pause.getBoundingClientRect();
let pause_start_gapX = 10;
start.style.left = `${pause_rect.left+pause_rect.width+pause_start_gapX}px`;
start.style.top = `${pause_rect.top}px`;
function click_control(){
    console.log('clicked!')
    let control = this;
    let control_rect = control.getBoundingClientRect();
    let delta = 10;
    new Promise(function(resolve){
        control.style.top = `${control_rect.top+delta}px`;
        setTimeout(resolve, 100);
    }).then(result => {
        control.style.top = `${control_rect.top}px`;
    })

    if (control === pause){
        foods.forEach(food => food.isPause=true);
        s1.isPause = true;
    } else if (control === start){
        let pause_foods = foods.filter(food => food.isPause == true);
        if (pause_foods.length != foods.length || s1.isPause==false) return;

        foods.forEach(food => food.isPause=false);
        s1.isPause = false;

        foods.forEach(food => food_run(food));
        snake_run(s1);
    }
}
click_control = limit_frequence(click_control, 500);
pause.onclick = click_control;
start.onclick = click_control;


window.onresize = function(){
    { //resize_container
        pos_left = window.innerWidth/2 - container_rect.width/2;
        container.style.left = `${pos_left}px`;
        container_rect = container.getBoundingClientRect();
    }

    { //resize_wheel_wheel_pan
        wheel.style.left = `${container_rect.left}px`;
        wheel.style.top = `${container_rect.top+container_rect.height-wheel_rect.height}px`;
        wheel_rect = wheel.getBoundingClientRect();
        wheel_pan.style.left = `${wheel_rect.left+wheel_rect.width-wheel_pan_rect.width/2}px`;
        wheel_pan.style.top = `${wheel_rect.top+wheel_rect.height/2-wheel_pan_rect.height/2}px`;
        wheel_center_x = wheel_rect.left + wheel_rect.width/2;
        wheel_center_y = wheel_rect.top + wheel_rect.height/2;
    }
}


//************

