export class Vector{
    constructor(dx, dy){
        this.x = dx;
        this.y = dy;
        this.len = Math.sqrt(this.x**2 + this.y**2);
    }

    add(vector){
        return new Vector(vector.x+this.x, vector.y+this.y);
    }

    sub(vector){
        return new Vector(this.x-vector.x, this.y-vector.y);
    }

    mul(number){
        return new Vector(this.x*number, this.y*number);
    }

    div(number){
        return new Vector(this.x/number, this.y/number);
    }

    unit(){
        return this.div(this.len);
    }

    dot(vector){
        return this.x*vector.x + this.y*vector.y;
    }

    toString(){
        return `<Vector x=${this.x} y=${this.y} len=${this.len}>`
    }
}