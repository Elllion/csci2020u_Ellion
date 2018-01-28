/*
 * ProGuard -- shrinking, optimization, obfuscation, and preverification
 *             of Java bytecode.
 *
 * Copyright (c) 2002-2018 GuardSquare NV
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package proguard.optimize.peephole;

import proguard.classfile.Clazz;
import proguard.classfile.constant.*;
import proguard.classfile.constant.visitor.ConstantVisitor;
import proguard.classfile.util.InstructionSequenceMatcher;

/**
 * This ConstantVisitor delegates to a given constant visitor, except for
 * constants that contain wildcards (indices larger than 0xffff).
 *
 * @see InstructionSequenceMatcher
 * @author Eric Lafortune
 */
public class WildcardConstantFilter
implements ConstantVisitor
{
    private static final int WILDCARD = InstructionSequenceMatcher.X;


    private final ConstantVisitor constantVisitor;


    /**
     * Creates a new WildcardClassReferenceInitializer that delegates to the
     * given constant visitor.
     */
    public WildcardConstantFilter(ConstantVisitor constantVisitor)
    {
        this.constantVisitor = constantVisitor;
    }


    // Implementations for ConstantVisitor.

    public void visitIntegerConstant(Clazz clazz, IntegerConstant integerConstant)
    {
        constantVisitor.visitIntegerConstant(clazz, integerConstant);
    }


    public void visitLongConstant(Clazz clazz, LongConstant longConstant)
    {
        constantVisitor.visitLongConstant(clazz, longConstant);
    }


    public void visitFloatConstant(Clazz clazz, FloatConstant floatConstant)
    {
        constantVisitor.visitFloatConstant(clazz, floatConstant);
    }


    public void visitDoubleConstant(Clazz clazz, DoubleConstant doubleConstant)
    {
        constantVisitor.visitDoubleConstant(clazz, doubleConstant);
    }


    public void visitPrimitiveArrayConstant(Clazz clazz, PrimitiveArrayConstant primitiveArrayConstant)
    {
        constantVisitor.visitPrimitiveArrayConstant(clazz, primitiveArrayConstant);
    }


    public void visitStringConstant(Clazz clazz, StringConstant stringConstant)
    {
        if (stringConstant.u2stringIndex < WILDCARD)
        {
            constantVisitor.visitStringConstant(clazz, stringConstant);
        }
    }


    public void visitUtf8Constant(Clazz clazz, Utf8Constant utf8Constant)
    {
        constantVisitor.visitUtf8Constant(clazz, utf8Constant);
    }


    public void visitInvokeDynamicConstant(Clazz clazz, InvokeDynamicConstant invokeDynamicConstant)
    {
        if (invokeDynamicConstant.u2nameAndTypeIndex < WILDCARD)
        {
            constantVisitor.visitInvokeDynamicConstant(clazz, invokeDynamicConstant);
        }
    }


    public void visitMethodHandleConstant(Clazz clazz, MethodHandleConstant methodHandleConstant)
    {
        constantVisitor.visitMethodHandleConstant(clazz, methodHandleConstant);
    }


    public void visitFieldrefConstant(Clazz clazz, FieldrefConstant fieldrefConstant)
    {
        if (fieldrefConstant.u2classIndex       < WILDCARD &&
            fieldrefConstant.u2nameAndTypeIndex < WILDCARD)
        {
            constantVisitor.visitFieldrefConstant(clazz, fieldrefConstant);
        }
    }


    public void visitInterfaceMethodrefConstant(Clazz clazz, InterfaceMethodrefConstant interfaceMethodrefConstant)
    {
        if (interfaceMethodrefConstant.u2classIndex       < WILDCARD &&
            interfaceMethodrefConstant.u2nameAndTypeIndex < WILDCARD)
        {
            constantVisitor.visitInterfaceMethodrefConstant(clazz, interfaceMethodrefConstant);
        }
    }


    public void visitMethodrefConstant(Clazz clazz, MethodrefConstant methodrefConstant)
    {
        if (methodrefConstant.u2classIndex       < WILDCARD &&
            methodrefConstant.u2nameAndTypeIndex < WILDCARD)
        {
            constantVisitor.visitMethodrefConstant(clazz, methodrefConstant);
        }
    }


    public void visitClassConstant(Clazz clazz, ClassConstant classConstant)
    {
        if (classConstant.u2nameIndex < WILDCARD)
        {
            constantVisitor.visitClassConstant(clazz, classConstant);
        }
    }


    public void visitMethodTypeConstant(Clazz clazz, MethodTypeConstant methodTypeConstant)
    {
        if (methodTypeConstant.u2descriptorIndex < WILDCARD)
        {
            constantVisitor.visitMethodTypeConstant(clazz, methodTypeConstant);
        }
    }


    public void visitNameAndTypeConstant(Clazz clazz, NameAndTypeConstant nameAndTypeConstant)
    {
        if (nameAndTypeConstant.u2nameIndex       < WILDCARD &&
            nameAndTypeConstant.u2descriptorIndex < WILDCARD)
        {
            constantVisitor.visitNameAndTypeConstant(clazz, nameAndTypeConstant);
        }
    }


    public void visitModuleConstant(Clazz clazz, ModuleConstant moduleConstant)
    {
        if (moduleConstant.u2nameIndex       < WILDCARD)
        {
            constantVisitor.visitModuleConstant(clazz, moduleConstant);
        }
    }


    public void visitPackageConstant(Clazz clazz, PackageConstant packageConstant)
    {
        if (packageConstant.u2nameIndex       < WILDCARD)
        {
            constantVisitor.visitPackageConstant(clazz, packageConstant);
        }
    }
}
