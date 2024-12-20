#ifdef GL_ES
#define LOWP lowp
    precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 u_circleCenter;
uniform float u_circleRadius;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    float dist = distance(v_texCoords, u_circleCenter);
    if (dist <= u_circleRadius) {
        texColor.rgb = 1.0 - texColor.rgb;
    }
    gl_FragColor = v_color * texColor;
}
