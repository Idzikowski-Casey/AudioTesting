//
// Created by Casey Idzikowski on 5/15/25.
//

#include <cmath>
#pragma once

// region sound helpers

class LowPassFilter {
public:
    explicit LowPassFilter(float alpha = 0.05f) : alpha(alpha), prev(0.0f) {}

    float process(float input) {
        prev = alpha * input + (1.0f - alpha) * prev;
        return prev;
    }

private:
    float alpha;
    float prev;
};

class CommonSounds {
public:
    CommonSounds() = default;

    // ranomd float between -1 and 1 for white noise generation
    static float whiteNoise() {
        return static_cast<float>(drand48() * 2.0 - 1.0);
    }

    // Clamp the amplitudes between bounds
    static inline float clampf(float value, float min, float max) {
        return std::max(min, std::min(max, value));
    }
};



// endregion