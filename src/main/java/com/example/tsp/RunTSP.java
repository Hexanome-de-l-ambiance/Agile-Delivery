package com.example.tsp;

import com.example.model.Carte;
import com.example.model.Livraison;
import com.example.xml.XMLOpener;

import java.util.ArrayList;

public class RunTSP {
	public static void main(String[] args) {
		TSP tsp = new TSP1();

		//runTSP(tsp);
		runTSP2(tsp);

	}

	public static void runTSP(TSP tsp) {
		for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			System.out.println("Graphs with "+nbVertices+" vertices:");
			Graph g = new RandomGraph(nbVertices);
			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, g);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println("0");
		}
	}

	public static void runTSP2(TSP tsp)
	{
		Carte carte = new Carte(1);
		XMLOpener xmlOpener = XMLOpener.getInstance();
		try{
			xmlOpener.readFile(carte, "data/xml/testMap.xml");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		carte.initAdjacenceList();

		ArrayList<Livraison> nodes = new ArrayList<Livraison>();
		nodes.add(new Livraison(carte.getListeIntersections().get(1L),8));
		nodes.add(new Livraison(carte.getListeIntersections().get(2L),8));
		nodes.add(new Livraison(carte.getListeIntersections().get(3L),8));

		Graph g = new CompleteGraph(carte, nodes);

		long startTime = System.currentTimeMillis();
		tsp.searchSolution(20000, g);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		for (int i=0; i<3; i++)
			System.out.print(tsp.getSolution(i)+" ");
		System.out.println("0");
	}

}
