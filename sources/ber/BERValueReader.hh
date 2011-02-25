#ifndef __BER_VALUE_READER_HH
#define __BER_VALUE_READER_HH

#include <ber/ASN1ValueReader.hh>
#include <ber/BERBuffer.hh>
#include <type/TypeCommon.hh>
#include <common/Utils.hh>

namespace asn1
{

class BERBuffer;

class BERValueReader : public ASN1ValueReader
{
public:
   explicit BERValueReader(BERBuffer& buffer)
      : _nestedReader(NULL), _buffer(buffer), _sequenceEndPos(0) {}

   // Reads BOOLEAN value
   void readBoolean(Boolean& value, const BooleanType& type);

   // Reads INTEGER value
   void readInteger(Integer& value, const IntegerType& type);

   // Reads OBJECT IDENTIFIER value
   void readObjectIdentifier(ObjectIdentifier& value, const ObjectIdentifierType& type);

   // Reads NULL value
   void readNull();

   // Reads OCTET STRING value
   void readOctetString(OctetString& value, const OctetStringType& type);

   // Reads VISIBLE STRING value
   void readVisibleString(OctetString& value, const VisibleStringType& type);

   // Reads SEQUENCE value
   void readSequenceBegin(const SequenceType& type);
   bool isSequenceEnd(const SequenceType& type);
   void readSequenceEnd(const SequenceType& type);

   // Reads SEQUENCE OF value (the same as SET)
   void readSequenceOfBegin(const SequenceType& type);
   bool isSequenceOfEnd(const SequenceType& type);
   void readSequenceOfEnd(const SequenceType& type);

   // Reads SET value
   void readSetBegin();
   void readSetEnd();

   // Reads SET OF value (the same as SET)
   void readSetOfBegin();
   void readSetOfEnd();

   // Reads EXPLICIT tag
   void readExplicitBegin(const Type& type);
   void readExplicitEnd(const Type& type);

protected:

   // Returns prototype (new instance) of the reader
   virtual BERValueReader* _prototype() const;

   // Reads and checks OCTET STRING value
   void _readOctetStringOctets(OctetString& value, const OctetStringType& type);

   // Checks tag for correctness
   void _checkTagIsCorrect(PCType pc, const Type& type);

   // Checks tag for tagging (IMPLICIT, EXPLICIT, ...)
   void _checkTagTagging(TagType tag, CLType cl, TagType expectedTag, const Type& type);

protected:

   BERValueReader* _nestedReader;  // nested BER reader, used to decode composition types
   BERBuffer&      _buffer;

private:

   BERBuffer::SizeType _sequenceEndPos;

   DISALLOW_COPY_AND_ASSIGN(BERValueReader);
};

}

#endif // __BER_VALUE_READER_HH