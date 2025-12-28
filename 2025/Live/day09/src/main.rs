fn part1(input: &String) {
    let mut points: Vec<(i64, i64)> = vec!();

    for line in input.lines() {
        let parts: Vec<&str> = line.split(",").collect();
        points.push((parts[0].parse().unwrap(), parts[1].parse().unwrap()));
    }

    let mut max_area: i64 = 0;
    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            //let rect = polygon
        }
    }
}

fn main() {
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
