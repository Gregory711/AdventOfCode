struct Point {
  x: i64,
  y: i64,
  z: i64
}

impl Point {
    fn distance_to(&self, other: &Point) -> i64 {
        return (other.x - self.x).pow(2) + (other.y - self.y).pow(2) + (other.z - self.z).pow(2);
    }
}

fn part1(input: &String, boxes_to_connect_count: i64) {
    let mut points: Vec<Point> = vec!();
    for line in input.lines() {
        let coords: Vec<&str> = line.split(',').collect();
        points.push(
            Point{
                x: coords[0].parse().unwrap(),
                y: coords[1].parse().unwrap(),
                z: coords[2].parse().unwrap()
            }
        );
    }
}

fn main() {
    let a = Point{x: 0, y: 0, z: 0};
    let b = Point{x: 1, y: 2, z: 3};
    // distance should be diff of each dimension squared combined which for this example
    // is 1*1 + 2*2 + 3*3 = 1 + 4 + 9 + 14
    if a.distance_to(&b) == 14 {
        println!("My distance function works! Pythagoras would be proud :)");
    } else {
        println!("ruh roh, my distance function is broken, expected 14, got: {}", a.distance_to(&b));
    }

    //let runs = vec![(&"test.txt", 10 as i64), (&"input.txt", 100 as i64)]; // literal vector of tuples :)
    let runs = vec![(&"test.txt", 10 as i64)];
    for (file, boxes_to_connect_count) in &runs {
        let input = std::fs::read_to_string(format!("day08/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}, {}:", file, boxes_to_connect_count);
        part1(&input, *boxes_to_connect_count);
    }
}
