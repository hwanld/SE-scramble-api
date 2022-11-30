package com.se.scramble.domain.dto.issues

class IssuesResponseDtoComparator : Comparator<IssuesResponseDto> {
    override fun compare(p0: IssuesResponseDto, p1: IssuesResponseDto): Int =
        if (p0.index > p1.index) 1
        else -1

}