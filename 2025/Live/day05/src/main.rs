struct Range {
    start: i64,
    end: i64,
}

fn number_in_a_range(number: i64, ranges: &Vec<Range>) -> bool {
    for range in ranges {
        if number >= range.start && number <= range.end {
            return true
        }
    }
    false
}

fn part1(input: &String) {
    let mut fresh_count: i64 = 0;
    let mut finished_reading_ranges = false;
    let mut ranges: Vec<Range> = vec!();

    for line in input.lines() {
        if !finished_reading_ranges && line.is_empty() {
            finished_reading_ranges = true;
        } else if !finished_reading_ranges {
            let range_parts: Vec<&str> = line.split("-").collect();
             ranges.push(Range{
                 start: range_parts[0].parse().unwrap(),
                 end: range_parts[1].parse().unwrap(),
             });
        } else {
            let number: i64 = line.parse().unwrap();
            if number_in_a_range(number, &ranges) {
                fresh_count += 1;
            }
        }
    }
    println!("The number of fresh items is {}", fresh_count);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day05/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
    }
}
