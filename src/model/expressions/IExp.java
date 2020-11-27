package model.expressions;

import model.collections.MyIDictionary;
import model.collections.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public interface IExp {
    /**
     * Evaluates the expression
     * @param tbl the symbol table of the program
     * @param heap the memory heap
     * @return the result of the expression as an IValue
     * @throws ExpressionEvaluationException if there is an error while evaluating
     */
    IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) throws ExpressionEvaluationException;

    /**
     * @return a deep copy of the current instance.
     */
    IExp copy();
}
