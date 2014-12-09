#ifndef JB_ESCAPER_H
#define JB_ESCAPER_H

#include <streambuf>
#include "Grammar.h"
#include "Value.h"

namespace JsonBox {
    class Escaper
    {
    public:
        Escaper();

        std::streambuf::int_type operator()(std::streambuf &destination,
                std::streambuf::int_type character);

    private:
        bool afterBackSlash;
        bool inString;
    };
}

#endif
