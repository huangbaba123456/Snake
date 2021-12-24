import {animate, bounce} from './animate_function.js';


{ // user
    let target_body = document.getElementById('target_body');
    let target = document.getElementById('target');
    let shrink = document.querySelector('#target_head span');
    shrink.onclick = (function(){
        let count = 0;
        return function(){
            count += 1;
            if (count % 2 != 0){
                this.innerHTML = '*';
                this.style.top = '-7px';
                target_body.style.transform = 'scale(0, 0)'
                target.style.background = 'none';
            } else {
                this.innerHTML = '^';
                this.style.top = '0px';
                target.style.background = 'rgb(63, 110, 197)';
                target_body.style.transform = 'scale(1, 1)'
            }
        }
    })();
}

{ // rank
    let rank_control = document.getElementById('rank_control');
    let rank_control_span = document.querySelector('#rank_control span');
    let rank = document.getElementById('rank');
    let rank_rect = rank.getBoundingClientRect();
    rank_control.style.left = `${rank_rect.left + rank_rect.width}px`;
    rank_control.style.top = `${rank_rect.top + rank_rect.height / 3}px`;
    rank_control.onclick = (function(){
        let count = 0;
        return function(){
            count += 1;
            if (count % 2 != 0){
                animate({
                    duration: 1500,
                    timing(timeFraction){
                        return 1 - bounce(1 - timeFraction);
                    },
                    draw(progress){
                        rank.style.left = `${-rank_rect.width*progress}px`
                        rank_rect = rank.getBoundingClientRect();
                        rank_control.style.left = `${rank_rect.left + rank_rect.width}px`;
                        rank_control.style.top = `${rank_rect.top + rank_rect.height / 3}px`;
                    }
                });
                rank_control_span.innerHTML = '>';
            } else {
                animate({
                    duration: 1500,
                    timing(timeFraction){
                        return 1 - bounce(1 - timeFraction);
                    },
                    draw(progress){
                        rank.style.left = `${-rank_rect.width + rank_rect.width*progress}px`
                        rank_rect = rank.getBoundingClientRect();
                        rank_control.style.left = `${rank_rect.left + rank_rect.width}px`;
                        rank_control.style.top = `${rank_rect.top + rank_rect.height / 3}px`;
                    }
                });
                rank_control_span.innerHTML = '<';
            }
        };
    })()
}




window.onresize = function(){
    back_resize();
}