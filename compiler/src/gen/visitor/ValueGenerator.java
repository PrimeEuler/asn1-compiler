package gen.visitor;

import gen.*;
import gen.utils.*;
import parser.*;

public class ValueGenerator extends DoNothingASTVisitor implements Generator {

   private ASTValueAssignment node = null;
   private boolean standAlone = false;
   private final CodeBuilder builder = new CodeBuilder();
   private GeneratorContext context = null;
   private String currentComponentName = null;

   public ValueGenerator(final ASTValueAssignment node) {
      this.node = node;
   }

   public ValueGenerator(final boolean standAlone) {
      this.standAlone = standAlone;
   }

   @Override
   public Object visit(ASTBuiltinType node, Object data) {
      return node.childrenAccept(this, data);
   }

   @Override
   public Object visit(ASTIntegerType node, Object data) {
      builder.append("enum { ");
      builder.append("k_").append(GenerationUtils.asCPPToken(this.node.getFirstToken().toString())).
              append(" = ");
      return data;
   }

   @Override
   public Object visit(ASTBooleanType node, Object data) {
      builder.append("#define ");
      builder.append(GenerationUtils.asCPPToken(this.node.getFirstToken().toString())).append(" ");
      return data;
   }

   @Override
   public Object visit(ASTOctetStringType node, Object data) {
      builder.append("#define ");
      builder.append(GenerationUtils.asCPPToken(this.node.getFirstToken().toString())).append(" \"");
      return data;
   }

   @Override
   public Object visit(ASTBuiltinValue node, Object data) {
      return node.childrenAccept(this, data);
   }

   @Override
   public Object visit(ASTBooleanValue node, Object data) {
      final String value = node.getFirstToken().toString();
      if ("true".equalsIgnoreCase(value)) {
         builder.append("true");
      } else if ("false".equalsIgnoreCase(value)) {
         builder.append("false");
      } else {
         throw new GeneratorException("Incorrect value: " + value);
      }
      if (!standAlone) {
         builder.newLine();
      }
      return data;
   }

   @Override
   public Object visit(ASTHexString node, Object data) {
      final String value = node.getFirstToken().toString();

      for (int i = 0; i <= (value.length() / 2) - 2; ++i) {
         final String hexNumber = String.copyValueOf(value.toCharArray(), ((i + 1) * 2) - 1, 2);
         final String octalNumber = Integer.toOctalString(Integer.valueOf(hexNumber, 16));

         builder.append("\\");
         for (int j = octalNumber.length(); j < 3; ++j) {
            builder.append('0');
         }

         builder.append(octalNumber);
      }
      builder.append("\"");
      builder.newLine();
      return data;
   }

   @Override
   public Object visit(ASTNameAndNumberForm node, Object data) {
      return node.childrenAccept(this, data);
   }

   @Override
   public Object visit(ASTNumberForm node, Object data) {
      return node.childrenAccept(this, data);
   }

   @Override
   public Object visit(ASTCompoundValue node, Object data) {
      return node.childrenAccept(this, data);
   }

   @Override
   public Object visit(ASTObjIdComponentList node, Object data) {
      builder.append("const ObjectIdentifierType::ValueType ").
              append(GenerationUtils.asCPPToken(currentComponentName)).append("(\"");
      VisitorUtils.visitChildsAndAccept(builder, node, new ObjIdComponentList(context));
      builder.append("\");");
      builder.newLine();
      return data;
   }

   @Override
   public Object visit(ASTBinaryString node, Object data) {
      final String value = node.getFirstToken().toString();

      for (int i = 0; i <= (value.length() / 4) - 2; ++i) {
         String binNumber = String.copyValueOf(value.toCharArray(),
                 (i * 8) + 1, (((i * 8) + 8 + 1 < value.length() - 2) ? 8
                 : value.length() - 2 - (i * 8 + 1)));
         for (int j = binNumber.length(); j < 8; ++j) {
            binNumber += "0";
         }
         final String octalNumber = Integer.toOctalString(Integer.valueOf(binNumber, 2));

         builder.append("\\");
         builder.append(octalNumber);
      }
      builder.append("\"");
      builder.newLine();
      return data;
   }

   @Override
   public Object visit(ASTSignedNumber node, Object data) {
      builder.append(node.getNumber());
      if (!standAlone) {
         builder.append(" };").newLine();
      }
      return data;
   }

   @Override
   public void generate(final GeneratorContext context) {
      this.context = context;
      builder.append("// ValueAssignment for ASN.1 value: ").append(node.getFirstToken().toString());
      builder.newLine();

      currentComponentName = node.getFirstToken().toString();
      node.childrenAccept(this, null);
      currentComponentName = null;

      builder.newLine();
   }

   public String getContent() {
      return builder.toString();
   }

   public boolean hasValuableContent() {
      return true;
   }
}
