struct Point {
    x: i64,
    y: i64
}

fn rect_area(corner_a: &Point, corner_b: &Point) -> i64 {
    return i64::abs(corner_a.x - corner_b.x) * i64::abs(corner_a.y - corner_b.y);
}

fn main() {
    let a = Point{ x: 2, y: 2 };
    let b = Point{ x: 3, y: 3 };
    let c = Point{ x: 0, y: 0 };
    if rect_area(&a, &b) == 1 && rect_area(&a, &c) == 4 {
        println!("rect_area is working as intended!");
    } else {
        println!("ruh roh");
    }
}
