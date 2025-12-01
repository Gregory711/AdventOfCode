// advent-2025 day 1

fn print_password(input: &String, part: i32) {
    let mut password: i64 = 0;
    let mut dial: i64 = 50;

    for line in input.lines() {
        let rotations: String = line.chars().skip(1).collect();
        let mut rotations: i64 = rotations.parse().unwrap();
        let dial_started_at_zero = dial == 0;

        // deal with full cycles
        if rotations >= 100 {
            // set rotations to remainder after full cycles are taken into account
            // if part 1 then we don't add full cycles to password, on part 2 we do
            if part == 2 {
                password += rotations / 100;
            }
            rotations %= 100;
        }

        // rotate dial
        if line.starts_with("L") {
            dial -= rotations;
        } else {
            dial += rotations;
        }

        // account for partial cycles
        // if on part 2 we add partial cycles to password (avoiding double counting)
        // only counts if clicked to zero not if started on zero, account for that
        if part == 2 && dial_started_at_zero && (dial < 0 || dial > 99) {
            password -= 1;
        }
        if dial < 0 {
            dial += 100;
            if part == 2 && dial != 0 {
                password += 1;
            }
        } else if dial > 99 {
            dial -= 100;
            if part == 2 && dial != 0 {
                password += 1;
            }
        }

        // increment password
        if dial == 0 {
            password += 1;
        }
        //println!("The line is {} and the password is {}", line, password);
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
        print_password(&input, 1);
        println!("Part 2: {}:", file);
        print_password(&input, 2);
    }
}
