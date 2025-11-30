fn part1(input: &String) {
    for line in input.lines() {
        println!("line = {}", line);
    }
}

fn main() {
    let input = std::fs::read_to_string("input.txt")
        .expect("Failed to read input file")
        .trim_end()
        .to_string();

    part1(&input)
}
