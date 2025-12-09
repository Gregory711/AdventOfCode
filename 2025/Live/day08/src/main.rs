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
}
