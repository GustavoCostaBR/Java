public class Percolation {

    private int grid[];
//	private int[] nodeSize;
    private int dimension;
    private int opensites;

//	public void printGrid() {
//		
//		System.out.println(this.grid[0]);
//		for (int i = 1; i < (this.dimension * this.dimension + 1); i++) {
//			System.out.print(this.grid[i] + "	");
//			if ((i % this.dimension) == 0) {
//				System.out.print("\n");
//			}
//		}
//	}

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid size");
        }
        this.opensites = 0;
        this.dimension = n;
        this.grid = new int[(n * n) + 1];
//		this.nodeSize = new int[(n * n) + 1];
        this.grid[0] = 0;
        for (int i = 1; i < this.grid.length; i++) {
            // for (int place : this.grid) {
//			place = 0;
            this.grid[i] = -2;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > this.dimension || col < 1 || col > this.dimension) {
            throw new IllegalArgumentException("Invalid row or column argument");
        }
        int position = (row - 1) * this.dimension + (col - 1) + 1;
        if (this.grid[position] == -2) {
            this.opensites++;
            int reference = comparison(position);
            unlimitedShorter(position, reference);
            if (this.dimension < 1) {
                return;
            }
            if (row == 1) {
                shorter(position + this.dimension, reference);
            } else if (row < this.dimension) {
                if (col == 1) {
                    shorter(position - this.dimension, reference);
                    shorter(position + 1, reference);
                    shorter(position + this.dimension, reference);
                } else if (col == this.dimension) {
                    shorter(position - this.dimension, reference);
                    shorter(position - 1, reference);
                    shorter(position + this.dimension, reference);
                } else {
                    shorter(position - this.dimension, reference);
                    shorter(position - 1, reference);
                    shorter(position + this.dimension, reference);
                    shorter(position + 1, reference);
                }
            } else {
                if (col == 1) {
                    // It was thought for all purposes
                    shorter(position - this.dimension, reference);
                    shorter(position + 1, reference);
                } else if (col == this.dimension) {
                    shorter(position - this.dimension, reference);
                    shorter(position - 1, reference);
                } else if (col > 1 && col < this.dimension) {
                    shorter(position - this.dimension, reference);
                    shorter(position - 1, reference);
                    shorter(position + 1, reference);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.dimension || col < 1 || col > this.dimension) {
            throw new IllegalArgumentException("Invalid row or column argument");
        }
        int position = (row - 1) * this.dimension + (col - 1) + 1;
        return (this.grid[position] != -2);
        
    }

    private int root(int position) {
        if (this.grid[position] != -2) {
            while (position != this.grid[position]) {
                position = this.grid[position];
            }
            return position;
        } else {
            return -2;
        }
    }

//	Return the minimum root among the ones 
    private int comparison(int position) {
        int col = ((position + (this.dimension - 1)) % this.dimension) + 1;
        int row = ((position + (this.dimension - 1)) / this.dimension);
        int temp = position;
        int tempUp;
        int tempRight;
        int tempLeft;
        int tempDown;

        if (row == 1) {
            temp = 0;
        } else if (row > 1 && row < this.dimension) {
            if (col == 1) {
//				It was thought for all purposes
                tempUp = root(position - this.dimension);
                tempRight = root(position + 1);
                tempDown = root(position + this.dimension);
                if (temp > tempUp && tempUp != -2) {
                    temp = tempUp;
                }
                if (temp > tempDown && tempDown != -2) {
                    temp = tempDown;
                }
                if (temp > tempRight && tempRight != -2) {
                    temp = tempRight;
                }
            } else if (col == this.dimension) {
                tempUp = root(position - this.dimension);
                tempLeft = root(position - 1);
                tempDown = root(position + this.dimension);
                if (temp > tempUp && tempUp != -2) {
                    temp = tempUp;
                }
                if (temp > tempDown && tempDown != -2) {
                    temp = tempDown;
                }
                if (temp > tempLeft && tempLeft != -2) {
                    temp = tempLeft;
                }
            } else if (col > 1 && col < this.dimension) {
                tempUp = root(position - this.dimension);
                tempLeft = root(position - 1);
                tempDown = root(position + this.dimension);
                tempRight = root(position + 1);
                if (temp > tempUp && tempUp != -2) {
                    temp = tempUp;
                }
                if (temp > tempDown && tempDown != -2) {
                    temp = tempDown;
                }
                if (temp > tempLeft && tempLeft != -2) {
                    temp = tempLeft;
                }
                if (temp > tempRight && tempRight != -2) {
                    temp = tempRight;
                }
            }
        } else if (row == this.dimension) {
            if (col == 1) {
//				It was thought for all purposes
                tempUp = root(position - this.dimension);
                tempRight = root(position + 1);
                if (temp > tempUp && tempUp != -2) {
                    temp = tempUp;
                }
                if (temp > tempRight && tempRight != -2) {
                    temp = tempRight;
                }
            } else if (col == this.dimension) {
                tempUp = root(position - this.dimension);
                tempLeft = root(position - 1);
                if (temp > tempUp && tempUp != -2) {
                    temp = tempUp;
                }
                if (temp > tempLeft && tempLeft != -2) {
                    temp = tempLeft;
                }
            } else if (col > 1 && col < this.dimension) {
                tempUp = root(position - this.dimension);
                tempLeft = root(position - 1);
                tempRight = root(position + 1);
                if (temp > tempUp && tempUp != -2) {
                    temp = tempUp;
                }
                if (temp > tempLeft && tempLeft != -2) {
                    temp = tempLeft;
                }
                if (temp > tempRight && tempRight != -2) {
                    temp = tempRight;
                }
            }
        }
        return temp;
    }

    // The reference must always be a root
    private void shorter(int position, int reference) {
        int temp;
        if (this.grid[position] == -2)
            return;
        while (position != this.grid[reference]) {
            temp = position;
            position = this.grid[position];
            this.grid[temp] = reference;
        }
        return;
    }

//	The reference must always be a root
    private void unlimitedShorter(int position, int reference) {
        this.grid[position] = reference;
        return;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.dimension || col < 1 || col > this.dimension) {
            throw new IllegalArgumentException("Invalid row or column argument");
        }
        int position = (row - 1) * this.dimension + (col - 1) + 1;
        if (position != -2) {
            int temp = root(position);
            return (temp == 0);
        } else
            return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        int verifierIndex = 0;
        while (verifierIndex < this.dimension) {
            int tempA = this.dimension * (this.dimension - 1) + verifierIndex + 1;
//			System.out.println("tempA igual a: " + tempA);
            if (root(tempA) == 0)
                return true;
            else
                verifierIndex++;
            continue;
        }
        return false;

    }

    // test client (optional)
    public static void main(String[] args) {

    }
}