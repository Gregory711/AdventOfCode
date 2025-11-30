// advent-2021 day 1

fn part1(input: &String) {
    let mut increases: i32 = 0;
    let mut last_number: Option<i64> = None;

    for line in input.lines() {
        let current_number: i64 = line.parse().unwrap();
        if last_number.is_some() && current_number > last_number.unwrap() {
            increases += 1;
        }
        last_number = Some(current_number);
    }

    println!("There were {} increases!", increases);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day01/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
    }
}
