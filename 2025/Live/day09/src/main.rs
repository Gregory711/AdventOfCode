struct Point {
    x: i64,
    y: i64
}

fn rect_area(corner_a: &Point, corner_b: &Point) -> i64 {
    return i64::abs(corner_a.x - corner_b.x) * i64::abs(corner_a.y - corner_b.y);
}

fn part1(input: &String) {
    let mut points: Vec<Point> = vec!();

    for line in input.lines() {
        let parts: Vec<&str> = line.split(",").collect();
        points.push(Point{ x: parts[0].parse().unwrap(), y: parts[1].parse().unwrap()});
    }
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

    //for &file in &["test.txt", "input.txt"] {
    for &file in &["test.txt"] {
        let input = std::fs::read_to_string(format!("day09/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
    }
}
