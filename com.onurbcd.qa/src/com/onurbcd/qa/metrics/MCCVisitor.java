package com.onurbcd.qa.metrics;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

public class MCCVisitor extends ASTVisitor {
	
	private int ifStatement;
	
	private int forStatement;
	
	private int enhancedForStatement;
	
	private int whileStatement;
	
	private int doStatement;
	
	private int switchCase;
	
	private int catchClause;
	
	private int conditionalExpression;
	
	public MCCVisitor() {
		ifStatement = 0;
		forStatement = 0;
		enhancedForStatement = 0;
		whileStatement = 0;
		doStatement = 0;
		switchCase = 0;
		catchClause = 0;
		conditionalExpression = 0;
	}

	@Override
	public boolean visit(IfStatement node) {
		ifStatement++;
        return true;
    }
	
	@Override
	public boolean visit(ForStatement node) {
		forStatement++;
        return true;
    }
	
	@Override
	public boolean visit(EnhancedForStatement node) {
		enhancedForStatement++;
        return true;
    }
	
	@Override
	public boolean visit(WhileStatement node) {
		whileStatement++;
        return true;
    }
	
	@Override
	public boolean visit(DoStatement node) {
		doStatement++;
        return true;
    }
	
	@Override
	public boolean visit(SwitchCase node) {
		switchCase++;
        return true;
    }
	
	@Override
	public boolean visit(CatchClause node) {
		catchClause++;
        return true;
    }
	
	@Override
	public boolean visit(ConditionalExpression node) {
		conditionalExpression++;
        return true;
    }

	public int getMcc() {
		return ifStatement + 
				forStatement +
				enhancedForStatement +
				whileStatement +
				doStatement +
				switchCase +
				catchClause +
				conditionalExpression;
	}
}
