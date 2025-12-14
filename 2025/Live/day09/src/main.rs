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

fn on_edge(point: &Point, row_edges: &HashMap<i64, Edge>, col_edges: &HashMap<i64, Edge>) -> bool {
    // check if the point is on an edge in a row
    if row_edges.contains_key(&point.y) {
        let row_edge: &Edge = row_edges.get(&point.y).unwrap();
        if point.x >= row_edge.start && point.x <= row_edge.end {
            return true;
        }
    }
    // check if the point is on an edge in a col
    if col_edges.contains_key(&point.x) {
        let col_edge: &Edge = col_edges.get(&point.x).unwrap();
        if point.y >= col_edge.start && point.y <= col_edge.end {
            return true;
        }
    }
    false
}

// can imagine this function as firing a laser beam at the point from left of the grid and
// counts how many times it intersects edges of the tile structure (not including the point itself)
fn edges_hit_count(point: &Point, row_edges: &HashMap<i64, Edge>, col_edges: &HashMap<i64, Edge>) -> i64 {
    let mut count: i64 = 0;
    // use riding_edge to avoid repeatedly counting the same edge if happen to be "riding" right
    // along it
    let mut riding_edge: bool = false;
    for x in 0..point.x {
        if on_edge(&Point{ x: x, y: point.y }, row_edges, col_edges) {
            if !riding_edge {
                count += 1;
                riding_edge = true;
            }
        } else {
            riding_edge = false;
        }
    }
    count
}

// Uses edges_hit_count to determine if provided point is inside (counting edges as inside) the
// red/green tiles section of the grid
// also counts as being inside red_green tiles if it ends on an edge even if even number of edges
// hit e.g. going through rectangle and ends on rightmost edge still considered inside the
// rectangle
fn point_inside_red_green_tiles(point: &Point, row_edges: &HashMap<i64, Edge>, col_edges: &HashMap<i64, Edge>) -> bool {
    return on_edge(&point, &row_edges, &col_edges) || (edges_hit_count(&point, &row_edges, &col_edges) % 2) == 1;
}

// problem: need to see if all the rectangle we are creating is completely enclosed
// by the red green tile shape
// algorithm:
// 1. Consider top left corner as A, top right as B, bottom left as C, and bottom
//    right as D
// 2. Iterate over points between (and including) A and B:
//  2.1. Shoot laser upwards from point and make sure count of edges intersected
//    starting from the point going to top of grid is an odd number to verify it is
//    enclosed by the red green shape
// 3. Repeat above process between C and D going downwards
// 4. Repeat between A and C going leftwards
// 5. Repeat between B and D going rightwards
// If at any point get even number then not enclosed and return false for entire
// operation!
fn rectangle_is_enclosed(a: &Point, b: &Point, c: &Point, d: &Point, row_edges: &HashMap<i64, Edge>, col_edges: &HashMap<i64, Edge>) -> bool {
    // A to B (so row (y) is fixed for starting points and x varies from A to B)
    for x in a.x..=b.x {
        //
    }

    // C to D
    for x in c.x..=d.x {
    }

    // A to C (so col (x) is fixed for starting points and y varies from A to C)
    for y in a.y..=c.y {
    }

    // B to D
    for y in b.y..=d.y {
    }

    true
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
    for point in &points {
        // upsert row edge if needed
        if !row_edges.contains_key(&point.y) {
            row_edges.insert(point.y, Edge{ start: point.x, end: point.x });
        } else {
            let row_edge: &Edge = row_edges.get(&point.y).unwrap();
            let updated_edge: Edge = Edge{ start: cmp::min(row_edge.start, point.x), end: cmp::max(row_edge.end, point.x) };
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
            let updated_edge: Edge = Edge{ start: cmp::min(col_edge.start, point.y), end: cmp::max(col_edge.end, point.y) };
            if col_edge.start != updated_edge.start || col_edge.end != updated_edge.end {
                col_edges.remove(&point.x);
                col_edges.insert(point.x, updated_edge);
            }
        }
    }

    println!("Starting point checks!");
    let mut max_area: i64 = 0;
    let mut enclose_checks_count: i64 = 0;
    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            // need to first calculate if these points would even result in a bigger area
            let temp_area: i64 = rect_area(&points[i], &points[j]);
            // if points would result in a bigger area than check every point in the rectangle to
            // verify they are all in the red_green_tiles section
            if temp_area > max_area {
                enclose_checks_count += 1;
                let mut all_inside: bool = true;

                // too slow start -----------
                for x in cmp::min(points[i].x, points[j].x)..=cmp::max(points[i].x, points[j].x) {
                    for y in cmp::min(points[i].y, points[j].y)..=cmp::max(points[i].y, points[j].y) {
                        if !point_inside_red_green_tiles(&Point{ x: x, y: y }, &row_edges, &col_edges) {
                            all_inside = false;
                            break;
                        }
                    }
                    if !all_inside {
                        break;
                    }
                }
                // too slow end ----------

                if all_inside {
                    max_area = temp_area;
                    //println!("Increasing max area to {} with ({}, {}), ({}, {})" , max_area, points[i].x, points[i].y, points[j].x, points[j].y);
                }
            }
            println!("j: {}/{}", j, points.len());
        }
        println!("i: {}/{}", i, points.len());
    }
    println!("Enclose checks made: {}", enclose_checks_count);

    println!("The max area inside red green tile area is: {}", max_area);
}

fn main() {
    // make sure rect_area works as expected
    let a = Point{ x: 2, y: 2 };
    let b = Point{ x: 3, y: 3 };
    let c = Point{ x: 0, y: 0 };
    let d = Point{ x: 3, y: 1 };
    if rect_area(&a, &b) == 4 && rect_area(&a, &c) == 9 {
        println!("rect_area is working as intended!");
    } else {
        println!("ruh roh: rect_area is not working as intended!");
    }

    // make sure on_edge works as expected
    let mut row_edges: HashMap<i64, Edge> = HashMap::new();
    let mut col_edges: HashMap<i64, Edge> = HashMap::new();
    row_edges.insert(2, Edge{ start: 0, end: 2 });
    col_edges.insert(3, Edge{ start: 1, end: 3 });
    if on_edge(&a, &row_edges, &col_edges) && on_edge(&b, &row_edges, &col_edges) && !on_edge(&c, &row_edges, &col_edges) {
        println!("on_edge is working as intended!");
    } else {
        println!("ruh roh: on_edge is not working as intended!");
    }

    // make sure edges_hit_count works as expected
    col_edges.insert(1, Edge{ start: 0, end: 2 });
    if edges_hit_count(&a, &row_edges, &col_edges) == 1 && edges_hit_count(&b, &row_edges, &col_edges) == 1 &&
        edges_hit_count(&c, &row_edges, &col_edges) == 0 && edges_hit_count(&d, &row_edges, &col_edges) == 1 {
        println!("edges_hit_count is working as intended!");
    } else {
        println!("ruh roh: edges_hit_count is not working as intended!");
        println!("edges_hit_count for a = {}", edges_hit_count(&a, &row_edges, &col_edges));
        println!("edges_hit_count for b = {}", edges_hit_count(&b, &row_edges, &col_edges));
        println!("edges_hit_count for c = {}", edges_hit_count(&c, &row_edges, &col_edges));
        println!("edges_hit_count for d = {}", edges_hit_count(&d, &row_edges, &col_edges));
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
