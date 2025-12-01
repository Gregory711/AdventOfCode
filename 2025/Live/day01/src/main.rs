// advent-2025 day 1

fn part1(input: &String) {
    let mut password: i64 = 0;
    let mut dial: i64 = 50;

    for line in input.lines() {
        let rotations: String = line.chars().skip(1).collect();
        let mut rotations: i64 = rotations.parse().unwrap();

        // deal with full cycles
        if rotations >= 100 {
            // set rotations to remainder after full cycles are taken into acccount
            rotations %= 100;
        }

        // rotate dial
        if line.starts_with("L") {
            dial -= rotations;
        } else {
            dial += rotations;
        }

        // account for partial cycles
        if dial < 0 {
            dial += 100;
        } else if dial > 99 {
            dial -= 100;
        }

        // increment password
        if dial == 0 {
            password += 1;
        }
    }
    println!("The password is: {}", password);
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
