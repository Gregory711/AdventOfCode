fn is_palindrome(number: i64) -> bool {
    let str_number: String = number.to_string();
    let char_array: Vec<char> = str_number.chars().collect();
    for i in 0..char_array.len()/2 {
        if char_array[i] != char_array[char_array.len() - i - 1] {
            return false;
        }
    }
    true
}

fn part1(input: &String) {
    let mut sum: i64 = 0;
    // all input is on the first line
    let line = input.lines().next().unwrap();
    //println!("{}", line);
    // get ranges
    let ranges = line.split(",");
    for range in ranges {
        //println!("{}", range);
        let range_parts: Vec<&str> = range.split("-").collect();
        let start: i64 = range_parts[0].parse().unwrap();
        let end: i64 = range_parts[0].parse().unwrap();
        for i in start..=end {
            if is_palindrome(i) {
                sum += i;
            }
        }
    }
    println!("Sum is {}", sum);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day02/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
    }
}
