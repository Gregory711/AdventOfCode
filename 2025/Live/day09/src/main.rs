use std::cmp;
use std::collections::HashMap;

struct Point {
    x: i64,
    y: i64
}

struct Edge {
    start: i64,
    end: i64
}

fn rect_area(corner_a: &Point, corner_b: &Point) -> i64 {
    return (i64::abs(corner_a.x - corner_b.x) + 1) * (i64::abs(corner_a.y - corner_b.y) + 1);
}

fn part1(input: &String) {
    let mut points: Vec<Point> = vec!();

    for line in input.lines() {
        let parts: Vec<&str> = line.split(",").collect();
        points.push(Point{ x: parts[0].parse().unwrap(), y: parts[1].parse().unwrap()});
    }

    let mut max_area: i64 = 0;
    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            max_area = cmp::max(max_area, rect_area(&points[i], &points[j]));
        }
    }

    println!("The max area is: {}", max_area);
}

fn part2(input: &String) {
    let mut points: Vec<Point> = vec!();

    for line in input.lines() {
        let parts: Vec<&str> = line.split(",").collect();
        points.push(Point{ x: parts[0].parse().unwrap(), y: parts[1].parse().unwrap()});
    }

    // row_edges are on a particular row (y) and start/stop on particular columns
    // likewise col_edges are on a particular column (x) and start/stop on particular rows
    let mut row_edges: HashMap<i64, Edge> = HashMap::new();
    let mut col_edges: HashMap<i64, Edge> = HashMap::new();
    for point in points {
        // upsert row edge if needed
        if !row_edges.contains_key(&point.y) {
            row_edges.insert(point.y, Edge{ start: point.x, end: point.x });
        } else {
            let row_edge: &Edge = row_edges.get(&point.y).unwrap();
            let updated_edge: Edge = Edge{ start: cmp::min(row_edge.start, point.y), end: cmp::max(row_edge.end, point.x) };
            if row_edge.start != updated_edge.start || row_edge.end != updated_edge.end {
                row_edges.remove(&point.y);
                row_edges.insert(point.y, updated_edge);
            }
        }

        // upsert col edge if needed
        if !col_edges.contains_key(&point.x) {
            col_edges.insert(point.x, Edge{ start: point.y, end: point.y });
        } else {
            let col_edge: &Edge = col_edges.get(&point.x).unwrap();
            let updated_edge: Edge = Edge{ start: cmp::min(col_edge.start, point.x), end: cmp::max(col_edge.end, point.y) };
            if col_edge.start != updated_edge.start || col_edge.end != updated_edge.end {
                col_edges.remove(&point.x);
                col_edges.insert(point.x, updated_edge);
            }
        }
    }
}

fn main() {
    let a = Point{ x: 2, y: 2 };
    let b = Point{ x: 3, y: 3 };
    let c = Point{ x: 0, y: 0 };
    if rect_area(&a, &b) == 4 && rect_area(&a, &c) == 9 {
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

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
