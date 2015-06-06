/*
 * Copyright 2015 Real Logic Ltd.
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

#ifndef AERON_POSITION_H
#define AERON_POSITION_H

#include "ReadOnlyPosition.h"

namespace aeron { namespace common { namespace concurrent { namespace status {

template <class X>
class Position : public ReadOnlyPosition<X>
{
public:
    inline void set(std::int64_t value)
    {
        ReadOnlyPosition<X>::m_impl.implSet(value);
    }

protected:
    Position(X& impl) : ReadOnlyPosition<X>(impl)
    {
    }
};

}}}}

#endif //AERON_POSITION_H