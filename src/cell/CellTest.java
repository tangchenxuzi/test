package cell;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class CellTest {
	private static Cell cell =new Cell(20,30);
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDeleteAllCell() {
		int[][] grid = cell.getGrid();
		cell.randomCell();
		cell.deleteAllCell();
	for(int i=0;i<20;i++)
		{
		for(int j=0;j<30;j++)
		{
			assertEquals(0,grid[i][j]);
		}
		}
	}

	@Test
	public void testUpdate() {
int[][] grid = cell.getGrid();
		
		grid[1][1]=1;
		grid[1][2]=1;
		grid[2][1]=1;
		grid[2][2]=1;
		cell.update();
		//每一个细胞都有三个邻居，都为生
		assertEquals(1,grid[2][2]);
		assertEquals(1,grid[1][1]);	
		assertEquals(1,grid[2][1]);	
		assertEquals(1,grid[1][2]);	
		grid[7][1]=1;
		grid[8][1]=1;
		grid[8][2]=1;
		assertEquals(3,cell.getNeighborCount(7, 2));
		cell.update();
		//有三个邻居，状态由死到生	
		assertEquals(1,grid[7][2]);
		grid[10][1]=1;
		grid[11][1]=1;
		assertEquals(2,cell.getNeighborCount(11, 2));
		cell.update();
		//有两个邻居，状态保持不变
		assertEquals(0,grid[11][2]);
		grid[10][1]=1;
		grid[11][1]=1;
		grid[11][2]=1;
		cell.update();
		assertEquals(1,grid[11][2]);
		grid[14][2]=1;
		grid[15][2]=1;
		assertEquals(1,cell.getNeighborCount(15, 2));
		cell.update();
		//只有一个邻居，状态由生到死
		assertEquals(0,grid[15][2]);
		grid[15][16]=1;
		grid[15][17]=1;
		grid[16][16]=1;
		grid[16][17]=1;
		grid[17][17]=1;
		assertEquals(4,cell.getNeighborCount(16, 16));
		cell.update();
		//超过三个邻居，状态变为死
		assertEquals(0,grid[16][16]);
		assertEquals(5,cell.getNeighborCount(16, 17));
		cell.update();
		//五个邻居的情况
		assertEquals(0,grid[16][17]);
		grid[9][20]=1;
		grid[9][21]=1;
		grid[9][22]=1;
		grid[10][22]=1;
		grid[11][22]=1;
		grid[11][21]=1;
		assertEquals(6,cell.getNeighborCount(10, 21));
		cell.update();
		//六个邻居的情况
		assertEquals(0,grid[10][21]);
		grid[9][23]=1;
		grid[11][23]=1;
		assertEquals(7,cell.getNeighborCount(10, 22));
		cell.update();
		//七个邻居
		assertEquals(0,grid[10][22]);
		grid[10][21]=1;
		grid[10][23]=1;
		grid[9][22]=1;
		assertEquals(8,cell.getNeighborCount(10, 22));
		//8个邻居
		cell.update();
		assertEquals(0,grid[10][22]);
		//0个邻居
		assertEquals(0,cell.getNeighborCount(18, 28));
		cell.update();
		assertEquals(0,grid[18][28]);
		
	}

	@Test
	public void testGetNeighborCount() {
		cell.deleteAllCell();
		assertEquals(0,cell.getNeighborCount(2, 2));
		int[][] grid = cell.getGrid();
		grid[1][1]=1;
		grid[1][2]=1;
		grid[2][1]=1;
		grid[2][2]=1;
		assertEquals(3,cell.getNeighborCount(2, 2));
		grid[5][5]=1;
		grid[5][6]=1;
		assertEquals(1,cell.getNeighborCount(5, 5));
		assertEquals(2,cell.getNeighborCount(4, 5));
	}
	@Test
	public void testRandomCell() {
		boolean flag=false;
		cell.randomCell();
		for (int i = 1; i <= 20; i++)
            for (int j = 1; j <= 30; j++)
            	if(cell.getGrid()[i][j]!=0)
            		flag=true;
		assertEquals(true,flag);           	
	}


}
