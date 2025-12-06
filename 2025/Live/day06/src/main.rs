fn part1(input: &String) {
    let mut grid: Vec<Vec<&str>> = vec![];
    let mut ops: Vec<&str> = vec![];

    // wrangle input
    for line in input.lines() {
        if line.contains('*') || line.contains('+') {
            ops = line.split_whitespace().collect();
        } else {
            grid.push(line.split_whitespace().collect());
        }
    }

    // calculate total
    let mut total: i64 = 0;
    for i in 0..ops.len() {
        let mut subtotal: i64 = grid[0][i].parse().unwrap();
        for j in 1..grid.len() {
            if ops[i].contains('*') {
                subtotal *= grid[j][i].parse::<i64>().unwrap();
            } else {
                subtotal += grid[j][i].parse::<i64>().unwrap();
            }
        }
        total += subtotal;
    }
    println!("The grand total is {}", total);
}

fn part2(input: &String) {
    let mut grid: Vec<Vec<char>> = vec![];
    let mut ops: Vec<char> = vec![];

    // wrangle input
    for line in input.lines() {
        if line.contains('*') || line.contains('+') {
            ops = line.chars().collect();
        } else {
            grid.push(line.chars().collect());
        }
    }

    // go backwards over input to build numbers and construct subtotals
    let mut total: i64 = 0;
    let mut numbers: Vec<i64> = vec![];
    for col in (0..ops.len()).rev() {
        // Build number
        let mut number_str: String = String::from("");
        for row in 0..grid.len() {
            // some numbers don't use all digits
            if grid[row][col] == ' ' {
                continue;
            }
            number_str.push(grid[row][col]);
        }
        // continue if this is empty column in between math problems
        if number_str.is_empty() {
            continue;
        }
        numbers.push(number_str.parse().unwrap());
        // If ops column then calculate subtotal and clear numbers vector
        if ops[col] != ' ' {
            let mut subtotal: i64 = numbers[0];
            for i in 1..numbers.len() {
                if ops[col] == '*' {
                    subtotal *= numbers[i];
                } else {
                    subtotal += numbers[i];
                }
            }
            numbers.clear();
            total += subtotal;
        }
    }
    println!("The grand total is {}", total);
}

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day06/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
