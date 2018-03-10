package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.google.common.base.FinalizablePhantomReference;
import com.google.common.collect.ImmutableList;

public class Board {
	
	private final List<Tiles> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	private Board(Builder builder) 
	{
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.White);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.Black);
		
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces); 
		
		
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		for(int i=0;i<BoardUtils.NUM_TILES;i++)
		{
			final String tileText = this.gameBoard.get(i).toString(); 
			builder.append(String.format("%s",tileText));
			if(i+1%BoardUtils.NUM_TILES_PER_ROW == 0)
			{
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}
	
	

	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) 
	{
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final Piece piece : pieces)
		{
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}

		return ImmutableList.copyOf(legalMoves);
	}

	private  Collection<Piece> calculateActivePieces(final List<Tiles> gameBoard,	final Alliance alliance) 
	{
		final List<Piece> activePieces = new ArrayList<>();
		for(final Tiles tile:gameBoard)
		{
			if(tile.isTileOccupied())
			{
				final Piece piece = tile.getPiece();
				if(piece.getPieceAlliance()==alliance)
					activePieces.add(piece);
			}
		}
		
		return ImmutableList.copyOf(activePieces);
	}

	private static List<Tiles> createGameBoard(final Builder builder)
	{
		final Tiles[] tiles = new Tiles[BoardUtils.NUM_TILES];
		for(int i=0;i<BoardUtils.NUM_TILES;i++)
		{
			tiles[i] = Tiles.createTiles(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);

	}

	public static Tiles getTile(final int tileCoordinate)
	{
		return null;
	}
	
	public static Board createStandardBoard()
	{
		final Builder builder = new Builder();
		//Black Layout
		builder.setPiece(new Rook(Alliance.Black,0));
		builder.setPiece(new Knight(Alliance.Black,1));
		builder.setPiece(new Bishop(Alliance.Black,2));
		builder.setPiece(new Queen(Alliance.Black,3));
		builder.setPiece(new King(Alliance.Black,4));
		builder.setPiece(new Bishop(Alliance.Black,5));
		builder.setPiece(new Knight(Alliance.Black,6));
		builder.setPiece(new Rook(Alliance.Black,7));
		builder.setPiece(new Pawn(Alliance.Black,8));
		builder.setPiece(new Pawn(Alliance.Black,9));
		builder.setPiece(new Pawn(Alliance.Black,10));
		builder.setPiece(new Pawn(Alliance.Black,11));
		builder.setPiece(new Pawn(Alliance.Black,12));
		builder.setPiece(new Pawn(Alliance.Black,13));
		builder.setPiece(new Pawn(Alliance.Black,14));
		builder.setPiece(new Pawn(Alliance.Black,15));
		
		//White Layout

		builder.setPiece(new Pawn(Alliance.White,48));
		builder.setPiece(new Pawn(Alliance.White,49));
		builder.setPiece(new Pawn(Alliance.White,50));
		builder.setPiece(new Pawn(Alliance.White,51));
		builder.setPiece(new Pawn(Alliance.White,52));
		builder.setPiece(new Pawn(Alliance.White,53));
		builder.setPiece(new Pawn(Alliance.White,54));
		builder.setPiece(new Pawn(Alliance.White,55));
		builder.setPiece(new Rook(Alliance.White,56));
		builder.setPiece(new Knight(Alliance.White,57));
		builder.setPiece(new Bishop(Alliance.White,58));
		builder.setPiece(new Queen(Alliance.White,59));
		builder.setPiece(new King(Alliance.White,60));
		builder.setPiece(new Bishop(Alliance.White,61));
		builder.setPiece(new Knight(Alliance.White,62));
		builder.setPiece(new Rook(Alliance.White,63));
		
		builder.setMoveMaker(Alliance.White);
		return builder.build();
	}
	
	public static class Builder
	{
		Map<Integer, Piece> boardConfig;
		Alliance nextMoveMaker;
		
		public Builder()
		{
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece)
		{
			this.boardConfig.put(piece.getPiecePoistion(), piece);
			return this;
		}
		
		public Builder setMoveMaker(final Alliance nextMoveMaker)
		{
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build()
		{
			return new Board(this);
		}
	}

}
