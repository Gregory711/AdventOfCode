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

    // create circuits using the cheapest boxes_to_connect_count number of connections
    let mut circuits: Vec<HashSet<Point>> = vec!();
    let mut added_connections_count: usize = 0;
    let mut i: usize = 0;
    while added_connections_count < boxes_to_connect_count {
        // add ith connection to existing circuit if possible i.e. if one point in connection
        // already in the circuit
        let mut added_to_circuit: bool = false;
        let mut combine_circuits: Vec<usize> = vec!();
        for (index, circuit) in circuits.iter_mut().enumerate() {
            if circuit.contains(&connections[i].a) && circuit.contains(&connections[i].b) {
                // case where both connections already in circuits
                added_to_circuit = true;
                combine_circuits.push(index);
            } else if added_to_circuit && (circuit.contains(&connections[i].a) || circuit.contains(&connections[i].b)) {
                // case where we already connected one of these points to a circuit so we can now
                // combine these circuits into one big circuit!
                // We'll do this by adding everything from this circuit to the prior circuit and
                // clear this circuit
                combine_circuits.push(index);
            } else if circuit.contains(&connections[i].a) || circuit.contains(&connections[i].b) {
                // case where we are just adding this connection to an existing circuit because it
                // shares a point
                if circuit.contains(&connections[i].a) {
                    //println!("Adding {} to a circuit", connections[i].b);
                    circuit.insert(connections[i].b.clone());
                } else {
                    //println!("Adding {} to a circuit", connections[i].a);
                    circuit.insert(connections[i].a.clone());
                }
                added_to_circuit = true;
                combine_circuits.push(index);
            }
        }
        if !added_to_circuit {
            //println!("Creating a new circuit using {} and {}", connections[i].a, connections[i].b);
            circuits.push(HashSet::from([connections[i].a.clone(), connections[i].b.clone()]));
        }
        // combine circuits!
        for index in 1..combine_circuits.len() {
            let circuits_clone = circuits[index].clone();
            for point in circuits_clone.iter().enumerate() {
                circuits[combine_circuits[0]].insert(point.1.clone());
            }
            circuits[index].clear();
        }
        i += 1;
        added_connections_count += 1;
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
    println!("There are {} circuits total", circuits.len());
    println!("From biggest to smallest the circuits have sizes:");
    for circuit_size in circuits_sizes {
        println!("{}", circuit_size);
    }
    println!("The result of multiplying the sizes of the three biggest circuits is: {}", product);
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

    let runs = vec![(&"test.txt", 10 as usize)];
    //let runs = vec![(&"test.txt", 10 as usize), (&"input.txt", 1000 as usize)]; // literal vector of tuples :)
        for (file, boxes_to_connect_count) in &runs {
        let input = std::fs::read_to_string(format!("day08/{}", file))
            .expect(&format!("Failed to read file: {}", file))
            .trim_end()
            .to_string();

        println!("Part 1: {}, {}:", file, boxes_to_connect_count);
        part1(&input, *boxes_to_connect_count);
    }
}
