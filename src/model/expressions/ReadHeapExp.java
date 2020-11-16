package model.expressions;

import model.collections.MyIDictionary;
import model.collections.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.InvalidParameterException;
import model.exceptions.InvalidTypeException;
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
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap<Integer, IValue> heap) throws ExpressionEvaluationException {
        var expResult = refExp.eval(tbl, heap);
        if (!(expResult.getType() instanceof RefType))
            throw new InvalidTypeException("Expression used in heap reading does not evaluate to RefValue");
        var expResultConv = (RefValue)expResult;

        var dataAddress = expResultConv.getAddress();
        try {
            if (!heap.containsKey(dataAddress))
                throw new ExpressionEvaluationException("Heap address does not exist");
            return heap.get(dataAddress);
        }
        catch (InvalidParameterException ignored) {}
        return null;
    }

    @Override
    public String toString() {
        return "rH(" + refExp + ")";
    }

    @Override
    public IExp copy() {
        return new ReadHeapExp(refExp.copy());
    }

    public @NotNull IExp getRefExp() {
        return refExp;
    }
}
