package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tiles;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece {

	private static final int[] CANDIDATE_MOVE_COORDINATES = {-17,-15-10,-6,6,10,15,17};
	
	public Knight(final Alliance pieceAlliance,final int piecePosition) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board)
	{
		int candidateDestinationCoordinate;
		final List<Move> legalMoves = new ArrayList<>();
		for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES)
		{
			candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			if( BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				if((isFirstColumnExclusion(this.piecePosition,currentCandidateOffset))
						|| (isSecondColumnExclusion(this.piecePosition,currentCandidateOffset))
						||(isSeventhColumnExclusion(this.piecePosition,currentCandidateOffset))
						||(isEightColumnExclusion(this.piecePosition,currentCandidateOffset)))
					continue;
				
				final Tiles candidateDestinationTiles = Board.getTile(candidateDestinationCoordinate);
				
				if(!candidateDestinationTiles.isTileOccupied())
				{
					legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate ));
				}
				else
				{
					final Piece pieceAtDestination = candidateDestinationTiles.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					if(this.pieceAlliance != pieceAlliance)
					{
						legalMoves.add(new Move.AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
					}
					
				}
				
			}
			
		}
		return ImmutableList.copyOf(legalMoves);
	}
	

	@Override
	public String toString()
	{
		return PieceType.KNIGHT.toString();
	}
	
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset==-17)||(candidateOffset == -10
				)||(candidateOffset == 6)|| (candidateOffset ==15));
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset)
	{
		return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset== 6)||(candidateOffset == -10));	 
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset)
	{
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset==-6)||(candidateOffset == 10));	 
	}
	
	private static boolean isEightColumnExclusion(final int currentPosition,final int candidateOffset)
	{
		return BoardUtils.EIGHT_COLUMN[currentPosition] && ((candidateOffset== 17)||(candidateOffset == 10
				)||(candidateOffset == -6)|| (candidateOffset ==-15));	 
	}

}
