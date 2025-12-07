use std::collections::HashSet;
use std::collections::HashMap;

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

fn part2(input: &String) {
    // mapping number of beams from any timeline heading down <row index, beam count>
    let mut beams_from_above: HashMap<usize, usize> = HashMap::new();
    for line in input.lines() {
        let mut beams_to_below: HashMap<usize, usize> = HashMap::new();
        let row: Vec<char> = line.chars().collect();
        for col in 0..row.len() {
            if beams_from_above.contains_key(&col) {
                let add_beams: usize = *beams_from_above.get(&col).unwrap();
                if row[col] == '.' {
                    *beams_to_below.entry(col).or_insert(0) += add_beams;
                } else {
                    // split
                    *beams_to_below.entry(col - 1).or_insert(0) += add_beams;
                    *beams_to_below.entry(col + 1).or_insert(0) += add_beams;
                }
            } else if row[col] == 'S' {
                beams_to_below.insert(col, 1);
            }
        }
        beams_from_above = beams_to_below;
    }
    let mut count: usize = 0;
    for beams in beams_from_above.values() {
        count += beams;
    }
    println!("There are {} different timelines!", count);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day07/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
