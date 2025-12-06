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

fn main() {
    for &file in &["test.txt", "input.txt"] {
        let input = std::fs::read_to_string(format!("day06/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}:", file);
        part1(&input);
    }
}
