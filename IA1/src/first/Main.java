package first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static List<State> pd = new ArrayList<State>();
	static List<ForBfs> fila = new ArrayList<ForBfs>();
	static int it = 0;
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	static boolean check2(State a) {
		for(int i=0;i<pd.size();i++) {
			State b = pd.get(i);
			int con = 0;
			for(int j=0;j<3;j++) {
				for(int k=0;k<3;k++) {
					if(b.vec[j][k] == a.vec[j][k]) con++;
				}
			}
			
			if(con == 9) {
				return true;
			}
		}
		return false;
	}
	
	static boolean check(State state) {
		for(int i=0;i<3;i++) {
			for(int j=1;j<3;j++) {
				if(state.vec[i][j] - state.vec[i][j-1] != 1) return false;
			}
		}
		return true;
	}
	
	static void dfs(State state, int x, int y, int h) {
		if(check2(state)) return ;
		
		if(check(state)) {
			System.out.println("Achou dfs!");
			System.out.println("Iteracoes: " + it);
			System.out.println("Altura: " + h);
			System.exit(0);
		}
		pd.add(state);
		it++;
		
		for(int i=0;i<4;i++) {
			int xx = x + dx[i];
			int yy = y + dy[i];
			if(xx >= 0 && xx < 3 && yy >= 0 && yy < 3) {
				State aux = new State();
				
				for(int j=0;j<3;j++) {
					for(int k=0;k<3;k++) aux.vec[j][k] = state.vec[j][k];
				}

				int aa = aux.vec[x][y];
				aux.vec[x][y] = aux.vec[xx][yy];
				aux.vec[xx][yy] = aa;
				
				dfs(aux, xx, yy, h + 1);
			}
		}
		
	}
	
	static void iterativeDfs(State state, int x, int y, int h, int maximo) {
		if(check2(state)) return ;
		if(check(state)) {
			System.out.println("Achou iterativeDfs!");
			System.out.println("Iteracoes: " + it);
			System.out.println("Altura: " + h);
			System.exit(0);
		}
		if(h == maximo) {
			ForBfs a = new ForBfs(x, y, h);
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					a.vec[i][j] = state.vec[i][j];
				}
			}
			fila.add(a);
			return ;
		}
		
		pd.add(state);
		it++;
		for(int i=0;i<4;i++) {
			int xx = x + dx[i];
			int yy = y + dy[i];
			if(xx >= 0 && xx < 3 && yy >= 0 && yy < 3) {
				State aux = new State();
				for(int j=0;j<3;j++) {
					for(int k=0;k<3;k++) {
						aux.vec[j][k] = state.vec[j][k];
					}
				}
				int aa = aux.vec[x][y];
				aux.vec[x][y] = aux.vec[xx][yy];
				aux.vec[xx][yy] = aa;
				iterativeDfs(aux, xx, yy, h+1, maximo);
			}
			
		}
		
	}
	
	static void bfs(State state, int x, int y, int h) {
		List<ForBfs> q = new ArrayList<ForBfs>();
		ForBfs a = new ForBfs(x, y, h);
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				a.vec[i][j] = state.vec[i][j];
			}
		}
		
		q.add(a);
		
		while(!q.isEmpty()) {
			State aux = new State();
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					aux.vec[i][j] = q.get(0).vec[i][j];
				}
			}
			int xx = q.get(0).x;
			int yy = q.get(0).y;
			int hh = q.get(0).h;
			q.remove(0);
			for(int i=0;i<4;i++) {
				int xxx = xx + dx[i];
				int yyy = yy + dy[i];
				if(xxx >= 0 && xxx < 3 && yyy >= 0 && yyy < 3) {
					State aux2 = new State();
					for(int j=0;j<3;j++) {
						for(int k=0;k<3;k++) aux2.vec[j][k] = aux.vec[j][k];
					}
					
					int aa = aux2.vec[xxx][yyy];
					aux2.vec[xxx][yyy] = aux2.vec[xx][yy];
					aux2.vec[xx][yy] = aa;

					if(check(aux2)) {
						System.out.println("Achou bfs!");
						System.out.println("Iteracoes: " + it);
						System.out.println("Altura: " + (hh + 1));
						System.exit(0);
					} else if(!check2(aux2)){
						ForBfs aux3 = new ForBfs(xxx, yyy, hh+1);
						for(int j=0;j<3;j++) {
							for(int k=0;k<3;k++) {
								aux3.vec[j][k] = aux2.vec[j][k];
							}
						}
						q.add(aux3);
						pd.add(aux2);
						it++;
					} 
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int x = 0, y = 0;
		State state = new State();
		Scanner scan = new Scanner(System.in);
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				int a = scan.nextInt();
				state.vec[i][j] = a;
				if(a == 9) {
					x = i;
					y = j;
				}
			}
		}

		
		System.out.println("Escolha a opcao: ");
		System.out.println("1 - Dfs");
		System.out.println("2 - Bfs");
		System.out.println("3 - iterativeDfs");
		Integer op;
		op = scan.nextInt();
		
		System.out.println("Opcao selecionada: " + op.toString());
		System.out.println("Rodando...");
		
		if(op == 1) {
			dfs(state, x, y, 0);	
		} else if(op == 2) {
			bfs(state, x, y, 0);
		} else {
			iterativeDfs(state, x, y, 0, 5);
			while(!fila.isEmpty()) {
				State atual = new State();
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						atual.vec[i][j] = fila.get(0).vec[i][j];
					}
				}
				int xx = fila.get(0).x;
				int yy = fila.get(0).y;
				int hh = fila.get(0).h;
				fila.remove(0);
				iterativeDfs(atual, xx, yy, hh, hh*2);
			}
		}
	
		
	}

}
