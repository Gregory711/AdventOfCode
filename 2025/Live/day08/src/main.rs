use std::collections::HashSet;
use std::fmt;

#[derive(Clone, Debug, Eq, Ord, PartialEq, PartialOrd, Hash)]
struct Point {
  x: i64,
  y: i64,
  z: i64
}

impl Point {
    fn distance_to(&self, other: &Point) -> i64 {
        return (other.x - self.x).pow(2) + (other.y - self.y).pow(2) + (other.z - self.z).pow(2);
    }
}

impl fmt::Display for Point {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "({}, {}, {})", self.x, self.y, self.z)
    }
}

#[derive(Debug, Eq, Ord, PartialEq, PartialOrd)]
struct Connection {
    cost: i64,
    a: Point,
    b: Point
}

fn print_circuits(circuits: &Vec<HashSet<Point>>) {
    for circuit in circuits {
        println!("Circuit contains:");
        for point in circuit {
            println!("{}", point);
        }
    }
}

fn part1(input: &String, boxes_to_connect_count: usize) {
    let mut points: Vec<Point> = vec!();
    for line in input.lines() {
        let coords: Vec<&str> = line.split(',').collect();
        points.push(
            Point{
                x: coords[0].parse().unwrap(),
                y: coords[1].parse().unwrap(),
                z: coords[2].parse().unwrap()
            }
        );
    }

    // create vector containing all possible point pair combinations along with their cost for
    // sorting to find the cheapest boxes to connect
    let mut connections: Vec<Connection> = vec!();
    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            connections.push(
                Connection{
                    cost: points[i].distance_to(&points[j]),
                    a: points[i].clone(),
                    b: points[j].clone()
                }
            );
        }
    }

    connections.sort();

    // Make the boxes_to_connect_count cheapest connections and form circuits using those
    // Algorithm to do this is as follows:
    // Iterate over the boxes_to_connect_count cheapest connections and for each one:
    //   Combine that new connection into a new circuit with all other existing circuits that
    //   overlap with it at all i.e. contain any of the same points, like legos snapping together
    //   Then add this new mega circuit to list with remaining circuits and go to next iteration
    let mut circuits: Vec<HashSet<Point>> = vec!();
    for i in 0..boxes_to_connect_count {
        let mut new_circuit = HashSet::from([connections[i].a.clone(), connections[i].b.clone()]);
        let mut new_circuits: Vec<HashSet<Point>> = vec!();
        for circuit in circuits {
            // add circuit to new_circuit if there is overlap else add it wholesale to new list
            if !new_circuit.is_disjoint(&circuit) {
                // add everything from circuit to new_circuit!
                new_circuit.extend(circuit);
            } else {
                new_circuits.push(circuit);
            }
        }
        new_circuits.push(new_circuit);
        circuits = new_circuits;
    }

    // calculate the size of the circuits
    let mut circuits_sizes: Vec<usize> = vec!();
    for circuit in &circuits {
        circuits_sizes.push(circuit.len());
    }

    // sort biggest to smallest
    circuits_sizes.sort();
    circuits_sizes.reverse();
    
    let product = circuits_sizes[0] * circuits_sizes[1] * circuits_sizes[2];
    /*println!("There are {} circuits total", circuits.len());
    println!("From biggest to smallest the circuits have sizes:");
    for circuit_size in circuits_sizes {
        println!("{}", circuit_size);
    }*/
    println!("The result of multiplying the sizes of the three biggest circuits is: {}", product);
}

fn part2(input: &String) {
    let mut points: Vec<Point> = vec!();
    for line in input.lines() {
        let coords: Vec<&str> = line.split(',').collect();
        points.push(
            Point{
                x: coords[0].parse().unwrap(),
                y: coords[1].parse().unwrap(),
                z: coords[2].parse().unwrap()
            }
        );
    }

    // create vector containing all possible point pair combinations along with their cost for
    // sorting to find the cheapest boxes to connect
    let mut connections: Vec<Connection> = vec!();
    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            connections.push(
                Connection{
                    cost: points[i].distance_to(&points[j]),
                    a: points[i].clone(),
                    b: points[j].clone()
                }
            );
        }
    }

    connections.sort();

    // Make the cheapest connections and form circuits until all connections in one circuit using this
    // algorithm:
    // Iterate over the boxes_to_connect_count cheapest connections and for each one:
    //   Combine that new connection into a new circuit with all other existing circuits that
    //   overlap with it at all i.e. contain any of the same points, like legos snapping together
    //   Then add this new mega circuit to list with remaining circuits and check if:
    //     Only 1 circuit remaining and number of points in circuit == points.len then output
    //     solution
    // The solution is product of multiplying X coordinates of last two connected junction boxes
    // together
    let mut circuits: Vec<HashSet<Point>> = vec!();
    for i in 0..connections.len() {
        let mut new_circuit = HashSet::from([connections[i].a.clone(), connections[i].b.clone()]);
        let mut new_circuits: Vec<HashSet<Point>> = vec!();
        for circuit in circuits {
            // add circuit to new_circuit if there is overlap else add it wholesale to new list
            if !new_circuit.is_disjoint(&circuit) {
                // add everything from circuit to new_circuit!
                new_circuit.extend(circuit);
            } else {
                new_circuits.push(circuit);
            }
        }
        new_circuits.push(new_circuit.clone());
        circuits = new_circuits;
        if circuits.len() == 1 && new_circuit.len() == points.len() {
            println!("The product of final junction box connection to form single large circuit is {}", connections[i].a.x * connections[i].b.x);
            return
        }
    }
}

fn main() {
    let a = Point{x: 0, y: 0, z: 0};
    let b = Point{x: 1, y: 2, z: 3};
    // distance should be diff of each dimension squared combined which for this example
    // is 1*1 + 2*2 + 3*3 = 1 + 4 + 9 + 14
    if a.distance_to(&b) == 14 {
        println!("My distance function works! Pythagoras would be proud :)");
    } else {
        println!("ruh roh, my distance function is broken, expected 14, got: {}", a.distance_to(&b));
    }

    //let runs = vec![(&"test.txt", 10 as usize)];
    let runs = vec![(&"test.txt", 10 as usize), (&"input.txt", 1000 as usize)]; // literal vector of tuples :)
        for (file, boxes_to_connect_count) in &runs {
        let input = std::fs::read_to_string(format!("day08/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}, {}:", file, boxes_to_connect_count);
        part1(&input, *boxes_to_connect_count);

        println!("Part 2: {}:", file);
        part2(&input);
    }
}
