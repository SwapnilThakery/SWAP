package com.chess.engine.pieces;

import java.util.*;

import com.chess.engine.board.*;

import com.chess.engine.Alliance;

public abstract class Piece {

	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	
	Piece(final int piecePosition, final Alliance pieceAlliance )
	{
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
		// to do more work here
		this.isFirstMove = false;
	}
	
	public int getPiecePoistion()
	{
		return this.piecePosition;
	}
	
	public boolean isFirstMove()
	{
		return this.isFirstMove; 
		
	}
	
	public Alliance getPieceAlliance()
	{
		return this.pieceAlliance;
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	public enum PieceType
	{
		PAWN("P"), KNIGHT("N"),BISHOP("B"),ROOK("R"),QUEEN("Q"),KING("K");	
		private  String pieceName;
		PieceType(final String pieceName)
		{
			this. pieceName = pieceName;
		}
		
		@Override
		public String toString()
		{
			return this.pieceName;
		
		}
		
	}
	
}
