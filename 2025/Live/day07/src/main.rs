use std::collections::HashSet;

fn part1(input: &String) {
    let mut split_count: i64 = 0;
    let mut beams_from_above: HashSet<usize> = HashSet::new();
    for line in input.lines() {
        let mut beams_to_below: HashSet<usize> = HashSet::new();
        let row: Vec<char> = line.chars().collect();
        for col in 0..row.len() {
            if beams_from_above.contains(&col) {
                if row[col] == '.' {
                    beams_to_below.insert(col);
                } else {
                    // split
                    beams_to_below.insert(col - 1);
                    beams_to_below.insert(col + 1);
                    split_count += 1;
                }
            } else if row[col] == 'S' {
                beams_to_below.insert(col);
            }
        }
        beams_from_above = beams_to_below;
    }
    println!("The number if splits was {} that's bananas!", split_count);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day07/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
    }
}
