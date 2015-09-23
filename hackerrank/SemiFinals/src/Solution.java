import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Solution {
	
		public static void main(String args[]){
			long startTime = System.currentTimeMillis();
			Scanner in = new Scanner(System.in);
			int t = in.nextInt();
			for(int i = 0; i<t; i++){
				int n = in.nextInt();
				int m = in.nextInt();
				int k = in.nextInt();
				HashMap<Integer, Integer> cityToVisit = new HashMap<>();
				/* get cities to be visited */
				for(int j=0;j<k; j++){
					int tempV = in.nextInt();
					cityToVisit.put(tempV, tempV);
				}
				/* create graph */
				HashMap<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
				for(int j=0;j<m; j++){
					int city_1 = in.nextInt();
					int city_2 = in.nextInt();
					/* create new city and/or connect city  */
					if(graph.get(city_1) == null)
						graph.put(city_1, new ArrayList<Integer>());
					
					if(graph.get(city_2) == null)
						graph.put(city_2, new ArrayList<Integer>());
					graph.get(city_1).add(city_2);
					
				}
				findRoute(graph, cityToVisit);
			}
			in.close();
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);
		}
		
		public static void findRoute(HashMap<Integer, List<Integer>> graph, HashMap<Integer, Integer> cityToVisit){
			Map<Integer, Integer> map = new TreeMap<Integer, Integer>(cityToVisit);

			TreeMap<Integer, Integer> visited = new TreeMap<Integer, Integer>();
			ArrayList<Integer> path = new ArrayList<Integer>();
			
			for (Map.Entry<Integer, Integer> entry : map.entrySet())
			{
			    int city = entry.getKey();
			    visited.put(city, visited.size() + 1);
				path.add(city);
				int res = searchNext(city, city, graph, cityToVisit, visited, path);
				if(res == 0)
					break;
				else{
					visited.clear();
					path.clear();
				}
			}
			
			
			if(visited.isEmpty()){
				System.out.println("-1");
			}else{
				
				Set<Entry<Integer, Integer>> set = visited.entrySet();
		        List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(set);
		        Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>()
		        {
		            public int compare( Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2 )
		            {
		                return (o1.getValue()).compareTo( o2.getValue() );
		            }
		        } );
		        
				if(graph.get(new Integer(list.get(list.size() - 1).getKey())).contains(new Integer(list.get(0).getKey()))){
					// is cyclic
					for (Map.Entry<Integer, Integer> v : visited.entrySet()){
						System.out.print(v.getKey() + " ");
					}
				}
				else{
					
					for(Map.Entry<Integer, Integer> entry:list){
			        	System.out.print(entry.getKey() + " ");
			        }
				}
				System.out.println("");
			}
		}
		
		public static int searchNext(int startingCity, int currentCity, HashMap<Integer, List<Integer>> graph, HashMap<Integer, Integer> cityToVisit, TreeMap<Integer, Integer> visited, List<Integer> path){
			
			if(visited.size() == cityToVisit.size())
				return 0; // all city visited
			
			List<Integer> connectedCity = graph.get(new Integer(currentCity));
			
			if(connectedCity.isEmpty())
				return -1;

			for(int cCity : connectedCity){
				if(cCity != currentCity && !path.containsAll(new ArrayList<Integer>(Arrays.asList(currentCity, cCity)))){
					if(cityToVisit.containsKey(new Integer(cCity)) && !visited.containsKey(new Integer(cCity))){
						visited.put(cCity, visited.size() + 1);
					}
					path.add(cCity);
					int res = searchNext(startingCity, cCity, graph, cityToVisit, visited, path);	
					if(res == 0)
						return 0;
					else{
						visited.remove(new Integer(cCity));
						path.remove(new Integer(cCity));
					}	
				}
			}
			return -1;
		}
	}
