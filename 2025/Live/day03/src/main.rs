use std::cmp;

fn part1(input: &String) {
    let mut total_joltage: u32 = 0;

    for line in input.lines() {
        // tens place will be biggest digit left of the far right position
        // (first instance of that big digit so have most options for big ones place)
        let digits: Vec<char> = line.chars().collect();
        let mut tens_place: u32 = digits[0].to_digit(10).unwrap();
        let mut tens_index: usize = 0;
        for i in 1..(digits.len() - 1) {
            if digits[i].to_digit(10).unwrap() > tens_place {
                tens_place = digits[i].to_digit(10).unwrap();
                tens_index = i;
            }
        }
        // ones place is biggest digit right of tens place
        let mut ones_place: u32 = digits[tens_index + 1].to_digit(10).unwrap();
        for i in (tens_index + 2)..digits.len() {
            ones_place = cmp::max(ones_place, digits[i].to_digit(10).unwrap());
        }
        // update total_joltage
        total_joltage += (tens_place * 10) + ones_place;
    }
    println!("The total joltage is: {}", total_joltage);
}

fn part2(input: &String) {
    let mut total_joltage: u64 = 0;

    for line in input.lines() {
        let mut index = 0;
        for place_value in (1..13).rev() { // 12 down to 1s place
            // nth_place will be biggest digit left of farthest right digit by number of remaining digits
            // e.g. if deciding on 8s place it is farthest right digit that leaves 7 digits to the right of it
            // (first instance of that big digit so have most options for rest)
            let digits: Vec<char> = line.chars().collect();
            let mut nth_place: u64 = u64::from(digits[index].to_digit(10).unwrap());
            index += 1;
            for i in index..(digits.len() - place_value + 1) {
                if u64::from(digits[i].to_digit(10).unwrap()) > nth_place {
                    nth_place = u64::from(digits[i].to_digit(10).unwrap());
                    index = i + 1;
                }
            }
            //println!("index is: {}, adding: {}", index, nth_place * 10_u64.pow((place_value - 1).try_into().unwrap()));
            total_joltage += nth_place * 10_u64.pow((place_value - 1).try_into().unwrap());
        }
    }
    println!("The total joltage is: {}", total_joltage);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
    //for &file in &["test.txt"] {
        let input = std::fs::read_to_string(format!("day03/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
