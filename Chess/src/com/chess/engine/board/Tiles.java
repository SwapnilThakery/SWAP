package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

public abstract class Tiles
{
	protected final int tileCoordinate;
	private static final Map<Integer,EmptyTile> Empty_Tiles_Cache = createAllPossibleEmptyTiles();
	
	public static Tiles createTiles(final int tileCoordinate,final Piece piece)
	{
		return piece!=null? new OccupiedTile(tileCoordinate, piece) : Empty_Tiles_Cache.get(tileCoordinate);
	}
	
	private Tiles(final int tileCoordinate)
	{
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map createAllPossibleEmptyTiles() 
	{
		final Map<Integer,EmptyTile> emptyTileMap = new HashMap();
		
		for(int i=0;i<BoardUtils.NUM_TILES;i++)
		{
			emptyTileMap.put(i,new EmptyTile(i));
		}
		//return Collections.unmodifiableMap(emptyTileMap);
		return ImmutableMap.copyOf(emptyTileMap);
	}

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tiles
	{
		private EmptyTile(final int coordinate) 
		{
			super(coordinate);
		}
		
		@Override
		
		public String toString()
		{
			return "-";
		}

		@Override
		public boolean isTileOccupied()
		{
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
	}
	

	public static final class OccupiedTile extends Tiles
	{
		private final Piece pieceOnTile;

		private OccupiedTile(final int tileCoordinate,final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}

		@Override
		public String toString()
		{
			return 	getPiece().getPieceAlliance().isBlack() ? toString().toLowerCase() :
					getPiece().toString();
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return pieceOnTile;
		}
		
	}
	
}
