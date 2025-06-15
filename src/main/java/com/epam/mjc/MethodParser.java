package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        if (signatureString == null || signatureString.trim().isEmpty()) {
            return new MethodSignature("");
        }

        String trimmed = signatureString.trim();

        // Find the method name and arguments by locating the opening parenthesis
        int openParenIndex = trimmed.indexOf('(');
        if (openParenIndex == -1) {
            return new MethodSignature("");
        }

        // Extract the part before parentheses and the arguments part
        String beforeParens = trimmed.substring(0, openParenIndex).trim();
        String argumentsPart = trimmed.substring(openParenIndex + 1);

        // Remove closing parenthesis from arguments
        int closeParenIndex = argumentsPart.lastIndexOf(')');
        if (closeParenIndex != -1) {
            argumentsPart = argumentsPart.substring(0, closeParenIndex);
        }

        // Split the part before parentheses to get access modifier, return type, and method name
        MethodSignature signature = getMethodSignature(beforeParens);

        // Parse arguments
        List<MethodSignature.Argument> arguments = parseArguments(argumentsPart.trim());
        signature.getArguments().addAll(arguments);

        return signature;
    }

    private static MethodSignature getMethodSignature(String beforeParens) {
        String[] parts = beforeParens.split("\\s+");

        MethodSignature signature;
        String methodName;

        if (parts.length == 1) {
            // Only method name (no access modifier, no return type)
            methodName = parts[0];
            signature = new MethodSignature(methodName);
        } else if (parts.length == 2) {
            // return type + method name (no access modifier)
            String returnType = parts[0];
            methodName = parts[1];
            signature = new MethodSignature(methodName);
            signature.setReturnType(returnType);
        } else if (parts.length >= 3) {
            // access modifier + return type + method name
            String accessModifier = parts[0];
            String returnType = parts[1];
            methodName = parts[2];
            signature = new MethodSignature(methodName);
            signature.setAccessModifier(accessModifier);
            signature.setReturnType(returnType);
        } else {
            signature = new MethodSignature("");
        }
        return signature;
    }

    private List<MethodSignature.Argument> parseArguments(String argumentsPart) {
        List<MethodSignature.Argument> arguments = new ArrayList<>();

        if (argumentsPart.isEmpty()) {
            return arguments;
        }

        // Split by comma to get individual arguments
        String[] argStrings = argumentsPart.split(",");

        for (String argString : argStrings) {
            String trimmedArg = argString.trim();
            if (!trimmedArg.isEmpty()) {
                // Split each argument by space to get type and name
                String[] argParts = trimmedArg.split("\\s+");
                if (argParts.length >= 2) {
                    String type = argParts[0];
                    String name = argParts[1];
                    arguments.add(new MethodSignature.Argument(type, name));
                }
            }
        }

        return arguments;
    }
}