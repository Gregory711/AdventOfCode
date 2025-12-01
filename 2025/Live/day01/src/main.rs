// advent-2025 day 1

fn part1(input: &String) {
    for line in input.lines() {
        println!("Line: {}", line);
    }
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day01/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
        println!("Part 2: {}:", file);
    }
}
