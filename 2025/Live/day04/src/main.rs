use std::cmp;

fn part1(input: &String) {
   let mut forklift_can_reach: i32 = 0;
   let mut grid: Vec<Vec<char>> = vec![];

    for line in input.lines() {
        grid.push(line.chars().collect());
    }

    for row in 0..grid.len() {
        for col in 0..grid[row].len() {
            // skip position if there is no roll here
            if grid[row][col] != '@' {
                continue;
            }
            let mut adjacent_rolls: i32 = 0;
            // iterate around current position and count how many rolls there are
            for r in cmp::max(row as i32 - 1, 0)..=cmp::min(row as i32 + 1, grid.len() as i32 - 1) {
                for c in cmp::max(col as i32 - 1, 0)..=cmp::min(col as i32 + 1, grid[row].len() as i32 - 1) {
                    if grid[r as usize][c as usize] == '@' {
                        adjacent_rolls += 1;
                    }
                }
            }
            // decrement 1 to avoid counting the roll at current position
            adjacent_rolls -= 1;
            // if less than four adjacent rolls then the forklift can reach it!
            if adjacent_rolls < 4 {
                forklift_can_reach += 1;
            }
        }
    }
    println!("The forklift can reach {} rolls!", forklift_can_reach);
}

fn part2(input: &String) {
   let mut forklift_can_reach: i32 = 0;
   let mut grid: Vec<Vec<char>> = vec![];

    for line in input.lines() {
        grid.push(line.chars().collect());
    }

    let mut removed_rolls: i32 = 1;
    while removed_rolls > 0 {
        removed_rolls = 0;
        for row in 0..grid.len() {
            for col in 0..grid[row].len() {
                // skip position if there is no roll here
                if grid[row][col] != '@' {
                    continue;
                }
                let mut adjacent_rolls: i32 = 0;
                // iterate around current position and count how many rolls there are
                for r in cmp::max(row as i32 - 1, 0)..=cmp::min(row as i32 + 1, grid.len() as i32 - 1) {
                    for c in cmp::max(col as i32 - 1, 0)..=cmp::min(col as i32 + 1, grid[row].len() as i32 - 1) {
                        if grid[r as usize][c as usize] == '@' {
                            adjacent_rolls += 1;
                        }
                    }
                }
                // decrement 1 to avoid counting the roll at current position
                adjacent_rolls -= 1;
                // if less than four adjacent rolls then the forklift can reach it!
                if adjacent_rolls < 4 {
                    forklift_can_reach += 1;
                    removed_rolls += 1;
                    grid[row][col] = ' ';
                }
            }
        }
    }

    println!("The forklift can reach {} rolls!", forklift_can_reach);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day04/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
