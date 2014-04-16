/*
 * Copyright 2014 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.real_logic.aeron;

import uk.co.real_logic.aeron.admin.ChannelNotifiable;
import uk.co.real_logic.aeron.admin.TermBufferNotifier;
import uk.co.real_logic.aeron.util.concurrent.logbuffer.Reader;
import uk.co.real_logic.aeron.util.protocol.DataHeaderFlyweight;

import static uk.co.real_logic.aeron.Receiver.MessageFlags.NONE;

public class ReceiverChannel extends ChannelNotifiable
{
    private Reader[] readers;
    private final Receiver.DataHandler dataHandler;
    private final DataHeaderFlyweight dataHeader;

    public ReceiverChannel(final Destination destination, final long channelId, final Receiver.DataHandler dataHandler)
    {
        super(new TermBufferNotifier(), destination.destination(), channelId);
        this.dataHandler = dataHandler;
        dataHeader = new DataHeaderFlyweight();
    }

    public boolean matches(final String destination, final long channelId)
    {
        return this.destination.equals(destination) && this.channelId == channelId;
    }

    public void process() throws Exception
    {
        final Reader reader = readers[currentBuffer];
        reader.read((buffer, offset, length) ->
        {
            dataHeader.wrap(buffer, offset);
            dataHandler.onData(buffer, dataHeader.dataOffset(), dataHeader.sessionId(), NONE);
        });
    }

    protected void rollTerm()
    {

    }

    public void onBuffersMapped(final Reader[] readers)
    {
        this.readers = readers;
    }

}
