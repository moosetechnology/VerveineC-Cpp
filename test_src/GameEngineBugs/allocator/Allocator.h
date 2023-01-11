// Example from Urho3D project
#pragma once

#include <cstddef>

namespace Urho3D
{

struct AllocatorBlock
{
    i32 nodeSize_;
    i32 capacity_;
    AllocatorBlock* next_;
};

URHO3D_API AllocatorBlock* AllocatorInitialize(i32 nodeSize, i32 initialCapacity = 1);
URHO3D_API void AllocatorUninitialize(AllocatorBlock* allocator);

template <class T> class Allocator
{
private:
    AllocatorBlock* allocator_;

public:
    explicit Allocator(i32 initialCapacity = 0)
        : allocator_(nullptr)
    {
        if (initialCapacity)
            allocator_ = AllocatorInitialize((i32)sizeof(T), initialCapacity);
    }

    ~Allocator()
    {
        AllocatorUninitialize(allocator_);
    }

    /// Prevent copy construction.
    Allocator(const Allocator<T>& rhs) = delete;
    /// Prevent assignment.
    Allocator<T>& operator =(const Allocator<T>& rhs) = delete;

};

}
