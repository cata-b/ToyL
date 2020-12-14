package model.expressions;

import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.InvalidTypeException;
import model.exceptions.TypeCheckException;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;
import org.jetbrains.annotations.NotNull;

public class ReadHeapExp implements IExp {
    private final @NotNull IExp refExp;

    public ReadHeapExp(@NotNull IExp refExp) {
        this.refExp = refExp;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) throws ExpressionEvaluationException {
        var expResult = refExp.eval(tbl, heap);
        if (!(expResult.getType() instanceof RefType))
            throw new InvalidTypeException("Expression used in heap reading does not evaluate to RefValue");
        var expResultConv = (RefValue)expResult;

        var dataAddress = expResultConv.getAddress();
        if (!heap.containsKey(dataAddress))
            throw new ExpressionEvaluationException("Heap address does not exist");
        return heap.get(dataAddress);
    }

    @Override
    public String toString() {
        return "rH(" + refExp + ")";
    }

    @Override
    public IExp copy() {
        return new ReadHeapExp(refExp.copy());
    }

    @Override
    public IType typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        var type = refExp.typeCheck(typeEnvironment);
        if (!(type instanceof RefType))
            throw new TypeCheckException("Read heap expression: argument is not a reference type");
        return ((RefType)type).getInner();
    }

    public @NotNull IExp getRefExp() {
        return refExp;
    }
}
