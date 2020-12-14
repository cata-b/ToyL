package model.expressions;

import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.TypeCheckException;
import model.types.IType;
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

    IType typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException;
}
