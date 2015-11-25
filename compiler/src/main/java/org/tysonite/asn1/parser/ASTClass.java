/* Generated By:JJTree: Do not edit this line. ASTClass.java Version 6.1 */
 /* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.tysonite.asn1.parser;

public class ASTClass extends SimpleNode {

   int tagClass = ASTTaggedType.CONTEXT;

   public ASTClass(int id) {
      super(id);
   }

   public ASTClass(AsnParser p, int id) {
      super(p, id);
   }

   public void setTagClass(int tagClass) {
      this.tagClass = tagClass;
   }

   public boolean isClassSet() {
      return tagClass != -1;
   }

   public boolean isContext() {
      return tagClass == ASTTaggedType.CONTEXT;
   }

   public boolean isPrivate() {
      return tagClass == ASTTaggedType.PRIVATE;
   }

   public boolean isApplication() {
      return tagClass == ASTTaggedType.APPLICATION;
   }

   public boolean isUniversal() {
      return tagClass == ASTTaggedType.UNIVERSAL;
   }

   public int getTagClass() {
      return this.tagClass;
   }

   /**
    * Accept the visitor. *
    */
   public Object jjtAccept(AsnParserVisitor visitor, Object data) {

      return visitor.visit(this, data);
   }
}
/* JavaCC - OriginalChecksum=b83b40cebff553ef7b3fbac06efa69b1 (do not edit this line) */