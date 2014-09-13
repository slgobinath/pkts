/**
 * 
 */
package io.pkts.packet.sip.header;

import static io.pkts.packet.sip.impl.PreConditions.assertArgument;
import static io.pkts.packet.sip.impl.PreConditions.assertNotEmpty;
import io.pkts.buffer.Buffer;
import io.pkts.buffer.Buffers;
import io.pkts.packet.sip.SipParseException;
import io.pkts.packet.sip.header.impl.CSeqHeaderImpl;

/**
 * @author jonas@jonasborjesson.com
 */
public interface CSeqHeader extends SipHeader {

    Buffer NAME = Buffers.wrap("CSeq");

    Buffer getMethod();

    long getSeqNumber();

    @Override
    CSeqHeader clone();

    static CSeqHeaderBuilder with() {
        return new CSeqHeaderBuilder();
    }

    static class CSeqHeaderBuilder {

        private long cseq;
        private Buffer method;

        private CSeqHeaderBuilder() {
            // left empty intentionally
        }

        /**
         * 
         * @param cseq
         * @return
         * @throws SipParseException in case the specified sequence number is less than zero.
         */
        public CSeqHeaderBuilder cseq(final long cseq) throws SipParseException {
            assertArgument(cseq >= 0, "Sequence number must be greater or equal to zer");
            this.cseq = cseq;
            return this;
        }

        public CSeqHeaderBuilder method(final Buffer method) throws SipParseException {
            this.method = assertNotEmpty(method, "Method cannot be null or empty");
            return this;
        }

        public CSeqHeaderBuilder method(final String method) throws SipParseException {
            this.method = Buffers.wrap(assertNotEmpty(method, "Method cannot be null or empty"));
            return this;
        }

        public CSeqHeader build() {
            assertNotEmpty(this.method, "Method cannot be null or empty");
            return new CSeqHeaderImpl(cseq, method, null);
        }

    }

}
