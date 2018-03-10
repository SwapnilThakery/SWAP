package com.chess.engine.pieces;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tiles;
import com.google.common.collect.ImmutableList;

public class Bishop extends Piece
{
	
	private static final int[] CANDIDATE_VECTOR_MOVE_COORDINATES = {-9,-7,7,9};

	public Bishop(Alliance pieceAlliance,int piecePosition)
	{
		super(piecePosition, pieceAlliance);
	}

	public Collection<Move> calculateLegalMoves(final Board board)
	{
		final List<Move> legalMoves = new ArrayList<>();
		for(final int candidateCoordinateOffset:CANDIDATE_VECTOR_MOVE_COORDINATES)
		{
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
						||isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))
					break;
				candidateDestinationCoordinate += candidateCoordinateOffset;
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
				{
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
					break;
				}
				
			}
		}
		
		
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public String toString()
	{
		return PieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-9||candidateOffset==7);
	}
	
	private static boolean isEightColumnExclusion(final int currentPosition,final int candidateOffset)
	{
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset ==9||candidateOffset==-7);
	}
}
