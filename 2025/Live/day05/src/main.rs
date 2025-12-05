#[derive(Debug, Eq, Ord, PartialEq, PartialOrd)]
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


fn part2(input: &String) {
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
        }
    }

    ranges.sort();

    let mut i: usize;
    let mut removed = true;
    while removed {
        removed = false;
        i = 0;
        while i < (ranges.len() - 1) {
            // if ranges overlap we can combine them!
            // e.g. if ranges 3-5 and 4-6 then change to 3-6
            //println!("Comparing {} to {}", ranges[i+1].start, ranges[i].end);
            if ranges[i + 1].start <= ranges[i].end {
                ranges[i].end = ranges[i + 1].end;
                ranges.remove(i + 1);
                removed = true;
            }
            i += 1;
        }
    }

    let mut possible_fresh_count: i64 = 0;
    for range in ranges {
        possible_fresh_count += range.end - range.start + 1;
        //println!("Adding {} for {} to {}!", range.end - range.start + 1, range.start, range.end);
    }
    println!("The total possible fresh count is {}!", possible_fresh_count);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
    //for &file in &["test.txt"] {
        let input = std::fs::read_to_string(format!("day05/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
