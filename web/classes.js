"use strict";
(function(module) {
    if (typeof define === 'function' && define.amd) {
        define(['exports'], function(exports)  {
            module(exports);
        });
    } else if (typeof exports === 'object' && exports !== null && typeof exports.nodeName !== 'string') {
        module(exports);
    } else {
        module(typeof self !== 'undefined' ? self : this);
}
}(function($rt_exports) {
let $rt_seed = 2463534242,
$rt_nextId = () => {
    let x = $rt_seed;
    x ^= x << 13;
    x ^= x >>> 17;
    x ^= x << 5;
    $rt_seed = x;
    return x;
},
$rt_wrapFunction0 = f => function() {
    return f(this);
},
$rt_wrapFunction1 = f => function(p1) {
    return f(this, p1);
},
$rt_wrapFunction2 = f => function(p1, p2) {
    return f(this, p1, p2);
},
$rt_wrapFunction3 = f => function(p1, p2, p3) {
    return f(this, p1, p2, p3, p3);
},
$rt_wrapFunction4 = f => function(p1, p2, p3, p4) {
    return f(this, p1, p2, p3, p4);
},
$rt_mainStarter = f => (args, callback) => {
    if (!args) {
        args = [];
    }
    let javaArgs = $rt_createArray($rt_objcls(), args.length);
    for (let i = 0;i < args.length;++i) {
        javaArgs.data[i] = $rt_str(args[i]);
    }
    $rt_startThread(() => {
        f.call(null, javaArgs);
    }, callback);
},
$rt_eraseClinit = target => target.$clinit = () => {
},
$dbg_class = obj => {
    let cls = obj.constructor;
    let arrayDegree = 0;
    while (cls.$meta && cls.$meta.item) {
        ++arrayDegree;
        cls = cls.$meta.item;
    }
    let clsName = "";
    if (cls.$meta.primitive) {
        clsName = cls.$meta.name;
    } else {
        clsName = cls.$meta ? cls.$meta.name || "a/" + cls.name : "@" + cls.name;
    }
    while (arrayDegree-- > 0) {
        clsName += "[]";
    }
    return clsName;
},
$rt_classWithoutFields = superclass => {
    if (superclass === 0) {
        return function() {
        };
    }
    if (superclass === void 0) {
        superclass = $rt_objcls();
    }
    return function() {
        superclass.call(this);
    };
},
$rt_cls = cls => jl_Class_getClass(cls),
$rt_objcls = () => jl_Object,
$rt_callWithReceiver = f => function() {
    return f.apply(null, [this].concat(Array.prototype.slice.call(arguments)));
},
$rt_createcls = () => {
    return { $array : null, classObject : null, $meta : { supertypes : [], superclass : null } };
},
$rt_createPrimitiveCls = (name, binaryName) => {
    let cls = $rt_createcls();
    cls.$meta.primitive = true;
    cls.$meta.name = name;
    cls.$meta.binaryName = binaryName;
    cls.$meta.enum = false;
    cls.$meta.item = null;
    cls.$meta.simpleName = null;
    cls.$meta.declaringClass = null;
    cls.$meta.enclosingClass = null;
    return cls;
},
$rt_booleancls = $rt_createPrimitiveCls("boolean", "Z"),
$rt_charcls = $rt_createPrimitiveCls("char", "C"),
$rt_intcls = $rt_createPrimitiveCls("int", "I"),
$rt_voidcls = $rt_createPrimitiveCls("void", "V");
if (typeof BigInt !== 'function') {
} else if (typeof BigInt64Array !== 'function') {
} else {
}
let $rt_imul = Math.imul || function(a, b) {
    let ah = a >>> 16 & 0xFFFF;
    let al = a & 0xFFFF;
    let bh = b >>> 16 & 0xFFFF;
    let bl = b & 0xFFFF;
    return al * bl + (ah * bl + al * bh << 16 >>> 0) | 0;
},
Long_fromNumber = val => BigInt.asIntN(64, BigInt(val >= 0 ? Math.floor(val) : Math.ceil(val))),
Long_toNumber = val => Number(val),
$rt_createArray = (cls, sz) => {
    let data = new Array(sz);
    data.fill(null);
    return new ($rt_arraycls(cls))(data);
};
if (typeof BigInt64Array !== 'function') {
} else {
}
let $rt_createCharArray = sz => new $rt_charArrayCls(new Uint16Array(sz)),
$rt_createIntArray = sz => new $rt_intArrayCls(new Int32Array(sz)),
$rt_createBooleanArray = sz => new $rt_booleanArrayCls(new Int8Array(sz)),
$rt_arraycls = cls => {
    let result = cls.$array;
    if (result === null) {
        function JavaArray(data) {
            ($rt_objcls()).call(this);
            this.data = data;
        }
        JavaArray.prototype = Object.create(($rt_objcls()).prototype);
        JavaArray.prototype.type = cls;
        JavaArray.prototype.constructor = JavaArray;
        JavaArray.prototype.toString = function() {
            let str = "[";
            for (let i = 0;i < this.data.length;++i) {
                if (i > 0) {
                    str += ", ";
                }
                str += this.data[i].toString();
            }
            str += "]";
            return str;
        };
        JavaArray.prototype.$clone0 = function() {
            let dataCopy;
            if ('slice' in this.data) {
                dataCopy = this.data.slice();
            } else {
                dataCopy = new this.data.constructor(this.data.length);
                for (let i = 0;i < dataCopy.length;++i) {
                    dataCopy[i] = this.data[i];
                }
            }
            return new ($rt_arraycls(this.type))(dataCopy);
        };
        let name = "[" + cls.$meta.binaryName;
        JavaArray.$meta = { item : cls, supertypes : [$rt_objcls()], primitive : false, superclass : $rt_objcls(), name : name, binaryName : name, enum : false, simpleName : null, declaringClass : null, enclosingClass : null };
        JavaArray.classObject = null;
        JavaArray.$array = null;
        result = JavaArray;
        cls.$array = JavaArray;
    }
    return result;
},
$rt_stringPool_instance,
$rt_stringPool = strings => {
    $rt_stringClassInit();
    $rt_stringPool_instance = new Array(strings.length);
    for (let i = 0;i < strings.length;++i) {
        $rt_stringPool_instance[i] = $rt_intern($rt_str(strings[i]));
    }
},
$rt_s = index => $rt_stringPool_instance[index],
$rt_charArrayToString = (array, offset, count) => {
    let result = "";
    let limit = offset + count;
    for (let i = offset;i < limit;i = i + 1024 | 0) {
        let next = Math.min(limit, i + 1024 | 0);
        result += String.fromCharCode.apply(null, array.subarray(i, next));
    }
    return result;
},
$rt_str = str => str === null ? null : jl_String__init_5(str),
$rt_ustr = str => str === null ? null : str.$nativeString,
$rt_stringClassInit = () => jl_String_$callClinit(),
$rt_intern;
{
    $rt_intern = str => str;
}
let $rt_isInstance = (obj, cls) => obj instanceof $rt_objcls() && !!obj.constructor.$meta && $rt_isAssignable(obj.constructor, cls),
$rt_isAssignable = (from, to) => {
    if (from === to) {
        return true;
    }
    let map = from.$meta.assignableCache;
    if (typeof map === 'undefined') {
        map = new Map();
        from.$meta.assignableCache = map;
    }
    let cachedResult = map.get(to);
    if (typeof cachedResult !== 'undefined') {
        return cachedResult;
    }
    if (to.$meta.item !== null) {
        let result = from.$meta.item !== null && $rt_isAssignable(from.$meta.item, to.$meta.item);
        map.set(to, result);
        return result;
    }
    let supertypes = from.$meta.supertypes;
    for (let i = 0;i < supertypes.length;i = i + 1 | 0) {
        if ($rt_isAssignable(supertypes[i], to)) {
            map.set(to, true);
            return true;
        }
    }
    map.set(to, false);
    return false;
},
$rt_throw = ex => {
    throw $rt_exception(ex);
},
$rt_javaExceptionProp = Symbol("javaException"),
$rt_exception = ex => {
    let err = ex.$jsException;
    if (!err) {
        let javaCause = $rt_throwableCause(ex);
        let jsCause = javaCause !== null ? javaCause.$jsException : void 0;
        let cause = typeof jsCause === "object" ? { cause : jsCause } : void 0;
        err = new JavaError("Java exception thrown", cause);
        if (typeof Error.captureStackTrace === "function") {
            Error.captureStackTrace(err);
        }
        err[$rt_javaExceptionProp] = ex;
        ex.$jsException = err;
        $rt_fillStack(err, ex);
    }
    return err;
},
$rt_fillStack = (err, ex) => {
    if (typeof $rt_decodeStack === "function" && err.stack) {
        let stack = $rt_decodeStack(err.stack);
        let javaStack = $rt_createArray($rt_stecls(), stack.length);
        let elem;
        let noStack = false;
        for (let i = 0;i < stack.length;++i) {
            let element = stack[i];
            elem = $rt_createStackElement($rt_str(element.className), $rt_str(element.methodName), $rt_str(element.fileName), element.lineNumber);
            if (elem == null) {
                noStack = true;
                break;
            }
            javaStack.data[i] = elem;
        }
        if (!noStack) {
            $rt_setStack(ex, javaStack);
        }
    }
},
JavaError;
if (typeof Reflect === 'object') {
    let defaultMessage = Symbol("defaultMessage");
    JavaError = function JavaError(message, cause) {
        let self = Reflect.construct(Error, [void 0, cause], JavaError);
        Object.setPrototypeOf(self, JavaError.prototype);
        self[defaultMessage] = message;
        return self;
    }
    ;
    JavaError.prototype = Object.create(Error.prototype, { constructor : { configurable : true, writable : true, value : JavaError }, message : { get() {
        try {
            let javaException = this[$rt_javaExceptionProp];
            if (typeof javaException === 'object') {
                let javaMessage = $rt_throwableMessage(javaException);
                if (typeof javaMessage === "object") {
                    return javaMessage !== null ? javaMessage.toString() : null;
                }
            }
            return this[defaultMessage];
        } catch (e){
            return "Exception occurred trying to extract Java exception message: " + e;
        }
    } } });
} else {
    JavaError = Error;
}
let $rt_javaException = e => e instanceof Error && typeof e[$rt_javaExceptionProp] === 'object' ? e[$rt_javaExceptionProp] : null,
$rt_throwableMessage = t => jl_Throwable_getMessage(t),
$rt_throwableCause = t => jl_Throwable_getCause(t),
$rt_stecls = () => jl_StackTraceElement,
$rt_createStackElement = (className, methodName, fileName, lineNumber) => {
    {
        return null;
    }
},
$rt_setStack = (e, stack) => {
},
$rt_packageData = null,
$rt_packages = data => {
    let i = 0;
    let packages = new Array(data.length);
    for (let j = 0;j < data.length;++j) {
        let prefixIndex = data[i++];
        let prefix = prefixIndex >= 0 ? packages[prefixIndex] : "";
        packages[j] = prefix + data[i++] + ".";
    }
    $rt_packageData = packages;
},
$rt_metadata = data => {
    let packages = $rt_packageData;
    let i = 0;
    while (i < data.length) {
        let cls = data[i++];
        cls.$meta = {  };
        let m = cls.$meta;
        let className = data[i++];
        m.name = className !== 0 ? className : null;
        if (m.name !== null) {
            let packageIndex = data[i++];
            if (packageIndex >= 0) {
                m.name = packages[packageIndex] + m.name;
            }
        }
        m.binaryName = "L" + m.name + ";";
        let superclass = data[i++];
        m.superclass = superclass !== 0 ? superclass : null;
        m.supertypes = data[i++];
        if (m.superclass) {
            m.supertypes.push(m.superclass);
            cls.prototype = Object.create(m.superclass.prototype);
        } else {
            cls.prototype = {  };
        }
        let flags = data[i++];
        m.enum = (flags & 8) !== 0;
        m.flags = flags;
        m.primitive = false;
        m.item = null;
        cls.prototype.constructor = cls;
        cls.classObject = null;
        m.accessLevel = data[i++];
        let innerClassInfo = data[i++];
        if (innerClassInfo === 0) {
            m.simpleName = null;
            m.declaringClass = null;
            m.enclosingClass = null;
        } else {
            let enclosingClass = innerClassInfo[0];
            m.enclosingClass = enclosingClass !== 0 ? enclosingClass : null;
            let declaringClass = innerClassInfo[1];
            m.declaringClass = declaringClass !== 0 ? declaringClass : null;
            let simpleName = innerClassInfo[2];
            m.simpleName = simpleName !== 0 ? simpleName : null;
        }
        let clinit = data[i++];
        cls.$clinit = clinit !== 0 ? clinit : function() {
        };
        let virtualMethods = data[i++];
        if (virtualMethods !== 0) {
            for (let j = 0;j < virtualMethods.length;j += 2) {
                let name = virtualMethods[j];
                let func = virtualMethods[j + 1];
                if (typeof name === 'string') {
                    name = [name];
                }
                for (let k = 0;k < name.length;++k) {
                    cls.prototype[name[k]] = func;
                }
            }
        }
        cls.$array = null;
    }
},
$rt_startThread = (runner, callback) => {
    let result;
    try {
        result = runner();
    } catch (e){
        result = e;
    }
    if (typeof callback !== 'undefined') {
        callback(result);
    } else if (result instanceof Error) {
        throw result;
    }
};
function jl_Object() {
    this.$id$ = 0;
}
let jl_Object__init_ = $this => {
    return;
},
jl_Object__init_0 = () => {
    let var_0 = new jl_Object();
    jl_Object__init_(var_0);
    return var_0;
},
jl_Object_getClass = $this => {
    return jl_Class_getClass($this.constructor);
},
jl_Object_toString = $this => {
    let var$1, var$2, var$3;
    var$1 = jl_Class_getName(jl_Object_getClass($this));
    var$2 = jl_Integer_toHexString(jl_Object_identity($this));
    var$3 = jl_StringBuilder__init_();
    jl_StringBuilder_append(jl_StringBuilder_append0(jl_StringBuilder_append(var$3, var$1), 64), var$2);
    return jl_StringBuilder_toString(var$3);
},
jl_Object_identity = $this => {
    let $platformThis;
    $platformThis = $this;
    if (!$platformThis.$id$)
        $platformThis.$id$ = $rt_nextId();
    return $this.$id$;
},
jl_Object_clone = $this => {
    let var$1, $result, var$3;
    if (!$rt_isInstance($this, jl_Cloneable)) {
        var$1 = $this;
        if (var$1.constructor.$meta.item === null)
            $rt_throw(jl_CloneNotSupportedException__init_0());
    }
    $result = otp_Platform_clone($this);
    var$1 = $result;
    var$3 = $rt_nextId();
    var$1.$id$ = var$3;
    return $result;
};
function cpgv_GameScreen() {
    jl_Object.call(this);
    this.$myName = null;
}
let cpgv_GameScreen_myCurrentScreen = null,
cpgv_GameScreen_myCanvas = null,
cpgv_GameScreen_myOrigin = null,
cpgv_GameScreen__init_ = ($this, $theName) => {
    jl_Object__init_($this);
    $this.$myName = $theName;
},
cpgv_GameScreen_getCurrentScreen = () => {
    return cpgv_GameScreen_myCurrentScreen;
},
cpgv_GameScreen_setCurrentScreen = $theCurrentScreen => {
    cpgv_GameScreen_myCurrentScreen = $theCurrentScreen;
},
cpgv_GameScreen_setCanvas = $theCanvas => {
    cpgv_GameScreen_myCanvas = $theCanvas;
},
cpgv_GameScreen_setOrigin = $theOrigin => {
    cpgv_GameScreen_myOrigin = cpgu_Vector2__init_1($theOrigin);
},
cpgv_GameScreen_getWidth = () => {
    if (cpgv_GameScreen_myCanvas === null)
        return 0;
    return cpgv_GameScreen_myCanvas.$getWidth();
},
cpgv_GameScreen_getHeight = () => {
    if (cpgv_GameScreen_myCanvas === null)
        return 0;
    return cpgv_GameScreen_myCanvas.$getHeight();
};
function jl_Throwable() {
    let a = this; jl_Object.call(a);
    a.$message = null;
    a.$cause = null;
    a.$suppressionEnabled = 0;
    a.$writableStackTrace = 0;
}
let jl_Throwable__init_ = $this => {
    $this.$suppressionEnabled = 1;
    $this.$writableStackTrace = 1;
    $this.$fillInStackTrace();
},
jl_Throwable__init_1 = () => {
    let var_0 = new jl_Throwable();
    jl_Throwable__init_(var_0);
    return var_0;
},
jl_Throwable__init_0 = ($this, $message) => {
    $this.$suppressionEnabled = 1;
    $this.$writableStackTrace = 1;
    $this.$fillInStackTrace();
    $this.$message = $message;
},
jl_Throwable__init_2 = var_0 => {
    let var_1 = new jl_Throwable();
    jl_Throwable__init_0(var_1, var_0);
    return var_1;
},
jl_Throwable_fillInStackTrace = $this => {
    return $this;
},
jl_Throwable_getMessage = $this => {
    return $this.$message;
},
jl_Throwable_getCause = $this => {
    return $this.$cause === $this ? null : $this.$cause;
},
jl_Exception = $rt_classWithoutFields(jl_Throwable),
jl_Exception__init_ = $this => {
    jl_Throwable__init_($this);
},
jl_Exception__init_1 = () => {
    let var_0 = new jl_Exception();
    jl_Exception__init_(var_0);
    return var_0;
},
jl_Exception__init_0 = ($this, $message) => {
    jl_Throwable__init_0($this, $message);
},
jl_Exception__init_2 = var_0 => {
    let var_1 = new jl_Exception();
    jl_Exception__init_0(var_1, var_0);
    return var_1;
},
jl_RuntimeException = $rt_classWithoutFields(jl_Exception),
jl_RuntimeException__init_ = $this => {
    jl_Exception__init_($this);
},
jl_RuntimeException__init_1 = () => {
    let var_0 = new jl_RuntimeException();
    jl_RuntimeException__init_(var_0);
    return var_0;
},
jl_RuntimeException__init_0 = ($this, $message) => {
    jl_Exception__init_0($this, $message);
},
jl_RuntimeException__init_2 = var_0 => {
    let var_1 = new jl_RuntimeException();
    jl_RuntimeException__init_0(var_1, var_0);
    return var_1;
},
jl_IndexOutOfBoundsException = $rt_classWithoutFields(jl_RuntimeException),
jl_IndexOutOfBoundsException__init_ = $this => {
    jl_RuntimeException__init_($this);
},
jl_IndexOutOfBoundsException__init_0 = () => {
    let var_0 = new jl_IndexOutOfBoundsException();
    jl_IndexOutOfBoundsException__init_(var_0);
    return var_0;
},
ju_Arrays = $rt_classWithoutFields(),
ju_Arrays_copyOf = ($array, $length) => {
    let var$3, $result, $sz, $i;
    var$3 = $array.data;
    $result = $rt_createCharArray($length);
    $sz = jl_Math_min($length, var$3.length);
    $i = 0;
    while ($i < $sz) {
        $result.data[$i] = var$3[$i];
        $i = $i + 1 | 0;
    }
    return $result;
},
ju_Arrays_copyOf0 = ($original, $newLength) => {
    let var$3, $result, $sz, $i;
    var$3 = $original.data;
    $result = jlr_Array_newInstance(jl_Class_getComponentType(jl_Object_getClass($original)), $newLength);
    $sz = jl_Math_min($newLength, var$3.length);
    $i = 0;
    while ($i < $sz) {
        $result.data[$i] = var$3[$i];
        $i = $i + 1 | 0;
    }
    return $result;
},
ju_Arrays_fill = ($a, $fromIndex, $toIndex, $val) => {
    let var$5, var$6;
    if ($fromIndex > $toIndex)
        $rt_throw(jl_IllegalArgumentException__init_0());
    while ($fromIndex < $toIndex) {
        var$5 = $a.data;
        var$6 = $fromIndex + 1 | 0;
        var$5[$fromIndex] = $val;
        $fromIndex = var$6;
    }
},
cpg_Launcher = $rt_classWithoutFields(),
cpg_Launcher_$callClinit = () => {
    cpg_Launcher_$callClinit = $rt_eraseClinit(cpg_Launcher);
    cpg_Launcher__clinit_();
},
cpg_Launcher_main = $args => {
    let $game, var$3, var$4;
    cpg_Launcher_$callClinit();
    $game = cpgc_GameRoot__init_0();
    cpgc_GameRoot_start($game);
    var$3 = window;
    var$4 = cpg_Launcher$main$lambda$_1_0__init_0($game);
    var$3.addEventListener("beforeunload", otji_JS_function(otji_JSWrapper_unwrap(var$4), "handleEvent"));
},
cpg_Launcher_lambda$main$0 = ($game, $evt) => {
    cpg_Launcher_$callClinit();
    cpgc_GameRoot_stop($game);
},
cpg_Launcher__clinit_ = () => {
    return;
},
cpgm_GameObject = $rt_classWithoutFields(),
cpgm_GameObject__init_ = $this => {
    jl_Object__init_($this);
};
function cpgmr_Rigid2D() {
    let a = this; cpgm_GameObject.call(a);
    a.$myCache = null;
    a.$myMass = 0.0;
    a.$myPosition = null;
    a.$myOldPosition = null;
    a.$myAcceleration = null;
    a.$myImpulse = null;
    a.$myMoi = 0.0;
    a.$myAngularPos = 0.0;
    a.$myOldAngularPos = 0.0;
    a.$myAngularAccel = 0.0;
    a.$myAngularImpulse = 0.0;
    a.$hasDynamics0 = 0;
    a.$myCollisions = 0;
}
let cpgmr_Rigid2D__init_ = ($this, $theCenterOfMass, $theMass) => {
    cpgm_GameObject__init_($this);
    $this.$myMass = $theMass;
    $this.$myCache = cpgu_Vector2__init_0();
    $this.$myPosition = cpgu_Vector2__init_1($theCenterOfMass);
    $this.$myOldPosition = cpgu_Vector2__init_1($this.$myPosition);
    $this.$myAcceleration = cpgu_Vector2__init_0();
    $this.$myImpulse = cpgu_Vector2__init_0();
},
cpgmr_Rigid2D_setDynamics = ($this, $theDynamics) => {
    $this.$hasDynamics0 = $theDynamics;
},
cpgmr_Rigid2D_applyGravity = $this => {
    let var$1;
    var$1 = $this.$myCache;
    cpgm_GameWorld_$callClinit();
    var$1.$set(cpgm_GameWorld_GRAVITY);
    $this.$myCache.$div($this.$myMass);
    $this.$myAcceleration.$add($this.$myCache);
},
cpgmr_Rigid2D_setLinearVelocity = ($this, $theVelocity) => {
    $this.$myCache.$set($this.$myPosition);
    $this.$myCache.$sub($theVelocity);
    $this.$myOldPosition.$set($this.$myCache);
},
cpgmr_Rigid2D_setAngularVelocity = ($this, $theAngVelocity) => {
    $this.$myOldAngularPos = $this.$myAngularPos - $theAngVelocity;
},
cpgmr_Rigid2D_applyImpulse = ($this, $theImpulseMag, $theDirection, $theDistance) => {
    let $jn;
    $jn = $theDirection.$mulNew($theImpulseMag);
    $this.$myImpulse.$add($jn);
    $this.$myAngularImpulse = $this.$myAngularImpulse + $jn.$crossProduct($theDistance);
},
cpgmr_Rigid2D_update = $this => {
    if (!$this.$hasDynamics0)
        return;
    $this.$preMove();
    $this.$move();
    $this.$postMove();
},
cpgmr_Rigid2D_preMove = $this => {
    if (!($this.$myImpulse.$getX() === 0.0 && $this.$myImpulse.$getY() === 0.0))
        $this.$setLinearVelocity(($this.$getLinearVelocity()).$addNew($this.$myImpulse.$divNew($this.$myMass)));
    if ($this.$myAngularImpulse !== 0.0)
        $this.$setAngularVelocity($this.$getAngularVelocity() + $this.$myAngularImpulse / $this.$myMoi);
},
cpgmr_Rigid2D_postMove = $this => {
    let $adjustment;
    $this.$myAngularImpulse = 0.0;
    $this.$myImpulse.$set0(0.0, 0.0);
    $this.$myAngularAccel = 0.0;
    $this.$myAcceleration.$set0(0.0, 0.0);
    $this.$myCollisions = 0;
    if (jl_Math_abs($this.$myAngularPos) > 6.28318531) {
        $adjustment = (-6.28318531) * jl_Math_signum($this.$myAngularPos);
        $this.$myAngularPos = $this.$myAngularPos + $adjustment;
        $this.$myOldAngularPos = $this.$myOldAngularPos + $adjustment;
    }
},
cpgmr_Rigid2D_getLinearVelocity = $this => {
    return $this.$myPosition.$subNew($this.$myOldPosition);
},
cpgmr_Rigid2D_getAngularVelocity = $this => {
    return $this.$myAngularPos - $this.$myOldAngularPos;
},
cpgmr_Rigid2D_getLinearAngularVelocity = ($this, $thePoint) => {
    return (($thePoint.$subNew($this.$myPosition)).$perpNew()).$mulNew($this.$getAngularVelocity());
},
cpgmr_Rigid2D_getCenterOfMass = $this => {
    return $this.$myPosition;
},
cpgmr_Rigid2D_getSplitMass = $this => {
    if (!$this.$myCollisions)
        return $this.$myMass;
    return $this.$myMass / $this.$myCollisions;
},
cpgmr_Rigid2D_getMoi = $this => {
    return $this.$myMoi;
},
cpgmr_Rigid2D_hasDynamics = $this => {
    return $this.$hasDynamics0;
},
cpgmr_Rigid2D_incCollision = $this => {
    if ($this.$hasDynamics0)
        $this.$myCollisions = $this.$myCollisions + 1 | 0;
};
function cpgmr_RigidBody() {
    let a = this; cpgmr_Rigid2D.call(a);
    a.$myVertices = null;
    a.$myEdges = null;
}
let cpgmr_RigidBody__init_0 = ($this, $theMass, $theVertices) => {
    cpgmr_RigidBody__init_($this, cpgu_VMath_findCentroid($theVertices), $theMass, $theVertices);
},
cpgmr_RigidBody__init_ = ($this, $theCenterOfMass, $theMass, $theVertices) => {
    let var$4, var$5, $i, var$7, var$8, var$9, var$10, $i_0;
    var$4 = $theVertices.data;
    cpgmr_Rigid2D__init_($this, $theCenterOfMass, $theMass);
    var$5 = var$4.length;
    if (var$5 < 3)
        $rt_throw(jl_IllegalArgumentException__init_($rt_s(0)));
    $this.$myVertices = $theVertices;
    $this.$myEdges = $rt_createArray(cpgmr_RigidBodyEdge, var$5);
    $i = 0;
    while ($i < $this.$myEdges.data.length) {
        var$7 = $this.$myEdges.data;
        var$8 = new cpgmr_RigidBodyEdge;
        var$9 = $this.$myVertices.data[$i];
        var$10 = $this.$myVertices.data;
        $i_0 = $i + 1 | 0;
        cpgmr_RigidBodyEdge__init_(var$8, var$9, var$10[$i_0 % $this.$myEdges.data.length | 0]);
        var$7[$i] = var$8;
        $i = $i_0;
    }
    if (!cpgu_VMath_isConvex($this.$myEdges))
        $rt_throw(jl_IllegalArgumentException__init_($rt_s(1)));
    $this.$myMoi = cpgu_VMath_findMomentOfInertia($this.$myVertices, $theCenterOfMass, $this.$myMass);
},
cpgmr_RigidBody_translate = ($this, $theTranslation) => {
    cpgmr_RigidBody_translateBody($this, $theTranslation);
    $this.$myOldPosition.$add($theTranslation);
},
cpgmr_RigidBody_translateBody = ($this, $theTranslation) => {
    let var$2, var$3, var$4, $v;
    var$2 = $this.$myVertices.data;
    var$3 = var$2.length;
    var$4 = 0;
    while (var$4 < var$3) {
        $v = var$2[var$4];
        $v.$add($theTranslation);
        var$4 = var$4 + 1 | 0;
    }
    $this.$myPosition.$add($theTranslation);
},
cpgmr_RigidBody_rotateBody = ($this, $theRadians) => {
    let var$2, var$3, var$4, $v;
    var$2 = $this.$myVertices.data;
    var$3 = var$2.length;
    var$4 = 0;
    while (var$4 < var$3) {
        $v = var$2[var$4];
        cpgu_VMath_rotate($v, $this.$myPosition, $theRadians);
        var$4 = var$4 + 1 | 0;
    }
    $this.$myAngularPos = $this.$myAngularPos + $theRadians;
},
cpgmr_RigidBody_move = $this => {
    let $current;
    $this.$myCache.$set($this.$myPosition);
    cpgmr_RigidBody_translateBody($this, ($this.$myPosition.$subNew($this.$myOldPosition)).$addNew($this.$myAcceleration));
    $this.$myOldPosition.$set($this.$myCache);
    $current = $this.$myAngularPos;
    cpgmr_RigidBody_rotateBody($this, $this.$myAngularPos - $this.$myOldAngularPos + $this.$myAngularAccel);
    $this.$myOldAngularPos = $current;
},
cpgmr_RigidBody_getEdges = $this => {
    return $this.$myEdges;
},
cpgmr_RigidBody_getVertices = $this => {
    return $this.$myVertices;
},
cpgmr_RigidBody_getName = $this => {
    return $rt_s(2);
},
cpgmr_Box = $rt_classWithoutFields(cpgmr_RigidBody),
cpgmr_Box__init_0 = ($this, $theOrigin, $width, $height, $theMass) => {
    let var$5, var$6, var$7;
    var$5 = $rt_createArray(cpgu_Vector2, 4);
    var$6 = var$5.data;
    var$6[0] = cpgu_Vector2__init_1($theOrigin);
    var$7 = cpgu_Vector2__init_($width, 0.0);
    var$6[1] = $theOrigin.$addNew(var$7);
    var$7 = cpgu_Vector2__init_($width, $height);
    var$6[2] = $theOrigin.$addNew(var$7);
    var$7 = cpgu_Vector2__init_(0.0, $height);
    var$6[3] = $theOrigin.$addNew(var$7);
    cpgmr_RigidBody__init_0($this, $theMass, var$5);
},
cpgmr_Box__init_ = (var_0, var_1, var_2, var_3) => {
    let var_4 = new cpgmr_Box();
    cpgmr_Box__init_0(var_4, var_0, var_1, var_2, var_3);
    return var_4;
},
cpgmr_Box_accept = ($this, $theV) => {
    return $theV.$visit($this);
},
otj_JSObject = $rt_classWithoutFields(0),
otjde_EventListener = $rt_classWithoutFields(0);
function cpg_Launcher$main$lambda$_1_0() {
    jl_Object.call(this);
    this.$_010 = null;
}
let cpg_Launcher$main$lambda$_1_0__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_010 = var$1;
},
cpg_Launcher$main$lambda$_1_0__init_0 = var_0 => {
    let var_1 = new cpg_Launcher$main$lambda$_1_0();
    cpg_Launcher$main$lambda$_1_0__init_(var_1, var_0);
    return var_1;
},
cpg_Launcher$main$lambda$_1_0_handleEvent = (var$0, var$1) => {
    cpg_Launcher_lambda$main$0(var$0.$_010, var$1);
},
cpg_Launcher$main$lambda$_1_0_handleEvent$exported$0 = (var$1, var$2) => {
    var$1.$handleEvent(var$2);
},
jl_System = $rt_classWithoutFields(),
jl_System_currentTimeMillis = () => {
    return Long_fromNumber((new Date()).getTime());
};
function cpgmr_RigidBodyEdge() {
    let a = this; jl_Object.call(a);
    a.$myStart = null;
    a.$myEnd = null;
}
let cpgmr_RigidBodyEdge__init_ = ($this, $theStart, $theEnd) => {
    jl_Object__init_($this);
    $this.$myStart = $theStart;
    $this.$myEnd = $theEnd;
},
cpgmr_RigidBodyEdge__init_0 = (var_0, var_1) => {
    let var_2 = new cpgmr_RigidBodyEdge();
    cpgmr_RigidBodyEdge__init_(var_2, var_0, var_1);
    return var_2;
},
cpgmr_RigidBodyEdge_getStart = $this => {
    return $this.$myStart;
},
cpgmr_RigidBodyEdge_getEnd = $this => {
    return $this.$myEnd;
},
cpgmr_RigidBodyEdge_getEdge = $this => {
    return $this.$myEnd.$subNew($this.$myStart);
},
cpgmr_RigidBodyEdge_getPerp = $this => {
    return (cpgmr_RigidBodyEdge_getEdge($this)).$perpNew();
},
ji_Serializable = $rt_classWithoutFields(0),
jl_Number = $rt_classWithoutFields(),
jl_Number__init_ = $this => {
    jl_Object__init_($this);
},
jl_Comparable = $rt_classWithoutFields(0);
function jl_Integer() {
    jl_Number.call(this);
    this.$value0 = 0;
}
let jl_Integer_TYPE = null,
jl_Integer_integerCache = null,
jl_Integer_$callClinit = () => {
    jl_Integer_$callClinit = $rt_eraseClinit(jl_Integer);
    jl_Integer__clinit_();
},
jl_Integer__init_ = ($this, $value) => {
    jl_Integer_$callClinit();
    jl_Number__init_($this);
    $this.$value0 = $value;
},
jl_Integer__init_0 = var_0 => {
    let var_1 = new jl_Integer();
    jl_Integer__init_(var_1, var_0);
    return var_1;
},
jl_Integer_hashCode0 = $value => {
    jl_Integer_$callClinit();
    return $value;
},
jl_Integer_toHexString = $i => {
    jl_Integer_$callClinit();
    return otci_IntegerUtil_toUnsignedLogRadixString($i, 4);
},
jl_Integer_valueOf = $i => {
    jl_Integer_$callClinit();
    if ($i >= (-128) && $i <= 127) {
        jl_Integer_ensureIntegerCache();
        return jl_Integer_integerCache.data[$i + 128 | 0];
    }
    return jl_Integer__init_0($i);
},
jl_Integer_ensureIntegerCache = () => {
    let $j;
    jl_Integer_$callClinit();
    a: {
        if (jl_Integer_integerCache === null) {
            jl_Integer_integerCache = $rt_createArray(jl_Integer, 256);
            $j = 0;
            while (true) {
                if ($j >= jl_Integer_integerCache.data.length)
                    break a;
                jl_Integer_integerCache.data[$j] = jl_Integer__init_0($j - 128 | 0);
                $j = $j + 1 | 0;
            }
        }
    }
},
jl_Integer_hashCode = $this => {
    return jl_Integer_hashCode0($this.$value0);
},
jl_Integer_equals = ($this, $other) => {
    if ($this === $other)
        return 1;
    return $other instanceof jl_Integer && $other.$value0 == $this.$value0 ? 1 : 0;
},
jl_Integer_numberOfLeadingZeros = $i => {
    let $n, var$3, var$4;
    jl_Integer_$callClinit();
    if (!$i)
        return 32;
    $n = 0;
    var$3 = $i >>> 16 | 0;
    if (var$3)
        $n = 16;
    else
        var$3 = $i;
    var$4 = var$3 >>> 8 | 0;
    if (!var$4)
        var$4 = var$3;
    else
        $n = $n | 8;
    var$3 = var$4 >>> 4 | 0;
    if (!var$3)
        var$3 = var$4;
    else
        $n = $n | 4;
    var$4 = var$3 >>> 2 | 0;
    if (!var$4)
        var$4 = var$3;
    else
        $n = $n | 2;
    if (var$4 >>> 1 | 0)
        $n = $n | 1;
    return (32 - $n | 0) - 1 | 0;
},
jl_Integer__clinit_ = () => {
    jl_Integer_TYPE = $rt_cls($rt_intcls);
},
jl_CloneNotSupportedException = $rt_classWithoutFields(jl_Exception),
jl_CloneNotSupportedException__init_ = $this => {
    jl_Exception__init_($this);
},
jl_CloneNotSupportedException__init_0 = () => {
    let var_0 = new jl_CloneNotSupportedException();
    jl_CloneNotSupportedException__init_(var_0);
    return var_0;
},
jl_Character = $rt_classWithoutFields(),
jl_Character_TYPE = null,
jl_Character_characterCache = null,
jl_Character_$callClinit = () => {
    jl_Character_$callClinit = $rt_eraseClinit(jl_Character);
    jl_Character__clinit_();
},
jl_Character_forDigit = ($digit, $radix) => {
    jl_Character_$callClinit();
    if ($radix >= 2 && $radix <= 36 && $digit >= 0 && $digit < $radix)
        return $digit < 10 ? (48 + $digit | 0) & 65535 : ((97 + $digit | 0) - 10 | 0) & 65535;
    return 0;
},
jl_Character__clinit_ = () => {
    jl_Character_TYPE = $rt_cls($rt_charcls);
    jl_Character_characterCache = $rt_createArray(jl_Character, 128);
},
ju_Map = $rt_classWithoutFields(0),
jl_CharSequence = $rt_classWithoutFields(0),
jl_Error = $rt_classWithoutFields(jl_Throwable),
jl_Error__init_ = $this => {
    jl_Throwable__init_($this);
},
jl_Error__init_0 = () => {
    let var_0 = new jl_Error();
    jl_Error__init_(var_0);
    return var_0;
},
jl_LinkageError = $rt_classWithoutFields(jl_Error),
jl_LinkageError__init_ = $this => {
    jl_Error__init_($this);
},
jl_LinkageError__init_0 = () => {
    let var_0 = new jl_LinkageError();
    jl_LinkageError__init_(var_0);
    return var_0;
};
function cpgci_Mouse$addListenersCanvas$lambda$_12_1() {
    jl_Object.call(this);
    this.$_011 = null;
}
let cpgci_Mouse$addListenersCanvas$lambda$_12_1__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_011 = var$1;
},
cpgci_Mouse$addListenersCanvas$lambda$_12_1__init_0 = var_0 => {
    let var_1 = new cpgci_Mouse$addListenersCanvas$lambda$_12_1();
    cpgci_Mouse$addListenersCanvas$lambda$_12_1__init_(var_1, var_0);
    return var_1;
},
cpgci_Mouse$addListenersCanvas$lambda$_12_1_handleEvent = (var$0, var$1) => {
    cpgci_Mouse_onMouseUp(var$0.$_011, var$1);
},
cpgci_Mouse$addListenersCanvas$lambda$_12_1_handleEvent$exported$0 = (var$1, var$2) => {
    var$1.$handleEvent(var$2);
},
jl_Iterable = $rt_classWithoutFields(0),
ju_Collection = $rt_classWithoutFields(0),
ju_Set = $rt_classWithoutFields(0);
function cpgci_Mouse$addListenersCanvas$lambda$_12_2() {
    jl_Object.call(this);
    this.$_02 = null;
}
let cpgci_Mouse$addListenersCanvas$lambda$_12_2__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_02 = var$1;
},
cpgci_Mouse$addListenersCanvas$lambda$_12_2__init_0 = var_0 => {
    let var_1 = new cpgci_Mouse$addListenersCanvas$lambda$_12_2();
    cpgci_Mouse$addListenersCanvas$lambda$_12_2__init_(var_1, var_0);
    return var_1;
},
cpgci_Mouse$addListenersCanvas$lambda$_12_2_handleEvent = (var$0, var$1) => {
    cpgci_Mouse_onMouseMove(var$0.$_02, var$1);
},
cpgci_Mouse$addListenersCanvas$lambda$_12_2_handleEvent$exported$0 = (var$1, var$2) => {
    var$1.$handleEvent(var$2);
},
jl_StringIndexOutOfBoundsException = $rt_classWithoutFields(jl_IndexOutOfBoundsException),
jl_StringIndexOutOfBoundsException__init_ = $this => {
    jl_IndexOutOfBoundsException__init_($this);
},
jl_StringIndexOutOfBoundsException__init_0 = () => {
    let var_0 = new jl_StringIndexOutOfBoundsException();
    jl_StringIndexOutOfBoundsException__init_(var_0);
    return var_0;
};
function cpgci_Mouse$addListenersCanvas$lambda$_12_0() {
    jl_Object.call(this);
    this.$_03 = null;
}
let cpgci_Mouse$addListenersCanvas$lambda$_12_0__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_03 = var$1;
},
cpgci_Mouse$addListenersCanvas$lambda$_12_0__init_0 = var_0 => {
    let var_1 = new cpgci_Mouse$addListenersCanvas$lambda$_12_0();
    cpgci_Mouse$addListenersCanvas$lambda$_12_0__init_(var_1, var_0);
    return var_1;
},
cpgci_Mouse$addListenersCanvas$lambda$_12_0_handleEvent = (var$0, var$1) => {
    cpgci_Mouse_onMouseDown(var$0.$_03, var$1);
},
cpgci_Mouse$addListenersCanvas$lambda$_12_0_handleEvent$exported$0 = (var$1, var$2) => {
    var$1.$handleEvent(var$2);
};
function cpgc_GameplayController() {
    let a = this; jl_Object.call(a);
    a.$myGameWorld = null;
    a.$myCollisionManager = null;
    a.$myInputs = null;
}
let cpgc_GameplayController_debugMode = 0,
cpgc_GameplayController__init_ = ($this, $theInputs) => {
    let $myCache;
    jl_Object__init_($this);
    $this.$myInputs = $theInputs;
    $this.$myGameWorld = cpgm_GameWorld__init_0();
    $this.$myCollisionManager = cpgmc_CollisionManager__init_0($this.$myGameWorld);
    $myCache = cpgu_Vector2__init_0();
    cpgm_GameWorld_SCREEN_BOUNDARY.$set0(cpgv_GameScreen_getWidth() >> 1, cpgv_GameScreen_getHeight() >> 1);
    cpgm_GameWorld_GRAVITY.$set0(0.0, 0.1);
    cpgc_GameplayController_debugMode = 0;
    $myCache.$set0( -cpgm_GameWorld_SCREEN_BOUNDARY.$getX(), cpgm_GameWorld_SCREEN_BOUNDARY.$getY());
    $this.$myGameWorld.$addStaticObject(cpgmr_Box__init_($myCache, cpgv_GameScreen_getWidth(), 50.0, 1.0));
    $myCache.$set0( -cpgm_GameWorld_SCREEN_BOUNDARY.$getX() - 50.0,  -cpgm_GameWorld_SCREEN_BOUNDARY.$getY());
    $this.$myGameWorld.$addStaticObject(cpgmr_Box__init_($myCache, 50.0, cpgv_GameScreen_getHeight(), 1.0));
    $myCache.$set0(cpgm_GameWorld_SCREEN_BOUNDARY.$getX(),  -cpgm_GameWorld_SCREEN_BOUNDARY.$getY());
    $this.$myGameWorld.$addStaticObject(cpgmr_Box__init_($myCache, 50.0, cpgv_GameScreen_getHeight(), 1.0));
    $myCache.$set0( -cpgm_GameWorld_SCREEN_BOUNDARY.$getX(),  -cpgm_GameWorld_SCREEN_BOUNDARY.$getY() - 50.0);
    $this.$myGameWorld.$addStaticObject(cpgmr_Box__init_($myCache, cpgv_GameScreen_getWidth(), 50.0, 1.0));
},
cpgc_GameplayController__init_0 = var_0 => {
    let var_1 = new cpgc_GameplayController();
    cpgc_GameplayController__init_(var_1, var_0);
    return var_1;
},
cpgc_GameplayController_update = $this => {
    let var$1, $l, $c, $index, var$5, $dynObject, $p;
    var$1 = $this.$myInputs.$getMouse();
    cpgci_ClickType_$callClinit();
    if (var$1.$isButtonDown(cpgci_ClickType_LeftClick)) {
        $l = (jl_Math_random() * 5.0 | 0) + 2 | 0;
        $c = $l > 2 ? cpgmr_RegularPolygon__init_0($this.$myInputs.$getMousePos(), $l, 50.0, 1.0) : cpgmr_RigidCircle__init_0($this.$myInputs.$getMousePos(), 50.0, 1.0);
        $c.$setDynamics(1);
        $this.$myGameWorld.$addDynamicObject($c);
    }
    $index = 0;
    var$5 = ($this.$myGameWorld.$getDynamicObjects()).$iterator();
    while (var$5.$hasNext()) {
        $dynObject = var$5.$next();
        if ($dynObject instanceof cpgmr_Rigid2D) {
            $p = $dynObject;
            $p.$applyGravity();
            cpgmc_CollisionManager_detectCollisions($this.$myCollisionManager, $p, $index);
        }
        $index = $index + 1 | 0;
    }
    cpgmc_CollisionManager_handleCollisions($this.$myCollisionManager);
    ($this.$myGameWorld.$getDynamicObjects()).$forEach(cpgc_GameplayController$update$lambda$_1_0__init_0());
},
cpgc_GameplayController_getGameWorld = $this => {
    return $this.$myGameWorld;
},
cpgmcr_CollisionResponse = $rt_classWithoutFields(),
cpgmcr_CollisionResponse__init_ = $this => {
    jl_Object__init_($this);
},
otjb_TimerHandler = $rt_classWithoutFields(0);
function jl_AbstractStringBuilder() {
    let a = this; jl_Object.call(a);
    a.$buffer = null;
    a.$length0 = 0;
}
let jl_AbstractStringBuilder__init_0 = $this => {
    jl_AbstractStringBuilder__init_($this, 16);
},
jl_AbstractStringBuilder__init_1 = () => {
    let var_0 = new jl_AbstractStringBuilder();
    jl_AbstractStringBuilder__init_0(var_0);
    return var_0;
},
jl_AbstractStringBuilder__init_ = ($this, $capacity) => {
    jl_Object__init_($this);
    $this.$buffer = $rt_createCharArray($capacity);
},
jl_AbstractStringBuilder__init_2 = var_0 => {
    let var_1 = new jl_AbstractStringBuilder();
    jl_AbstractStringBuilder__init_(var_1, var_0);
    return var_1;
},
jl_AbstractStringBuilder_append = ($this, $obj) => {
    return $this.$insert($this.$length0, $obj);
},
jl_AbstractStringBuilder_insert = ($this, $index, $string) => {
    let $i, var$4, var$5;
    if ($index >= 0 && $index <= $this.$length0) {
        if ($string === null)
            $string = $rt_s(3);
        else if ($string.$isEmpty())
            return $this;
        $this.$ensureCapacity($this.$length0 + $string.$length() | 0);
        $i = $this.$length0 - 1 | 0;
        while ($i >= $index) {
            $this.$buffer.data[$i + $string.$length() | 0] = $this.$buffer.data[$i];
            $i = $i + (-1) | 0;
        }
        $this.$length0 = $this.$length0 + $string.$length() | 0;
        $i = 0;
        while ($i < $string.$length()) {
            var$4 = $this.$buffer.data;
            var$5 = $index + 1 | 0;
            var$4[$index] = $string.$charAt($i);
            $i = $i + 1 | 0;
            $index = var$5;
        }
        return $this;
    }
    $rt_throw(jl_StringIndexOutOfBoundsException__init_0());
},
jl_AbstractStringBuilder_append0 = ($this, $c) => {
    return $this.$insert0($this.$length0, $c);
},
jl_AbstractStringBuilder_insert1 = ($this, $index, $c) => {
    jl_AbstractStringBuilder_insertSpace($this, $index, $index + 1 | 0);
    $this.$buffer.data[$index] = $c;
    return $this;
},
jl_AbstractStringBuilder_insert0 = ($this, $index, $obj) => {
    return $this.$insert1($index, $obj === null ? $rt_s(3) : $obj.$toString());
},
jl_AbstractStringBuilder_ensureCapacity = ($this, $capacity) => {
    let $newLength;
    if ($this.$buffer.data.length >= $capacity)
        return;
    $newLength = $this.$buffer.data.length >= 1073741823 ? 2147483647 : jl_Math_max($capacity, jl_Math_max($this.$buffer.data.length * 2 | 0, 5));
    $this.$buffer = ju_Arrays_copyOf($this.$buffer, $newLength);
},
jl_AbstractStringBuilder_toString = $this => {
    return jl_String__init_6($this.$buffer, 0, $this.$length0);
},
jl_AbstractStringBuilder_insertSpace = ($this, $start, $end) => {
    let $sz, $i;
    $sz = $this.$length0 - $start | 0;
    $this.$ensureCapacity(($this.$length0 + $end | 0) - $start | 0);
    $i = $sz - 1 | 0;
    while ($i >= 0) {
        $this.$buffer.data[$end + $i | 0] = $this.$buffer.data[$start + $i | 0];
        $i = $i + (-1) | 0;
    }
    $this.$length0 = $this.$length0 + ($end - $start | 0) | 0;
},
jl_Appendable = $rt_classWithoutFields(0),
jl_StringBuilder = $rt_classWithoutFields(jl_AbstractStringBuilder),
jl_StringBuilder__init_0 = $this => {
    jl_AbstractStringBuilder__init_0($this);
},
jl_StringBuilder__init_ = () => {
    let var_0 = new jl_StringBuilder();
    jl_StringBuilder__init_0(var_0);
    return var_0;
},
jl_StringBuilder_append = ($this, $obj) => {
    jl_AbstractStringBuilder_append($this, $obj);
    return $this;
},
jl_StringBuilder_append0 = ($this, $c) => {
    jl_AbstractStringBuilder_append0($this, $c);
    return $this;
},
jl_StringBuilder_insert2 = ($this, $index, $obj) => {
    jl_AbstractStringBuilder_insert0($this, $index, $obj);
    return $this;
},
jl_StringBuilder_insert1 = ($this, $index, $c) => {
    jl_AbstractStringBuilder_insert1($this, $index, $c);
    return $this;
},
jl_StringBuilder_insert3 = ($this, $index, $string) => {
    jl_AbstractStringBuilder_insert($this, $index, $string);
    return $this;
},
jl_StringBuilder_toString = $this => {
    return jl_AbstractStringBuilder_toString($this);
},
jl_StringBuilder_ensureCapacity = ($this, var$1) => {
    jl_AbstractStringBuilder_ensureCapacity($this, var$1);
},
jl_StringBuilder_insert0 = ($this, var$1, var$2) => {
    return $this.$insert2(var$1, var$2);
},
jl_StringBuilder_insert = ($this, var$1, var$2) => {
    return $this.$insert3(var$1, var$2);
},
jl_StringBuilder_insert4 = ($this, var$1, var$2) => {
    return $this.$insert4(var$1, var$2);
};
function jl_Enum() {
    let a = this; jl_Object.call(a);
    a.$name0 = null;
    a.$ordinal0 = 0;
}
let jl_Enum__init_ = ($this, $name, $ordinal) => {
    jl_Object__init_($this);
    $this.$name0 = $name;
    $this.$ordinal0 = $ordinal;
},
jl_Enum_ordinal = $this => {
    return $this.$ordinal0;
},
cpgci_ClickType = $rt_classWithoutFields(jl_Enum),
cpgci_ClickType_MiddleClick = null,
cpgci_ClickType_LeftClick = null,
cpgci_ClickType_RightClick = null,
cpgci_ClickType_SideButton1 = null,
cpgci_ClickType_SideButton2 = null,
cpgci_ClickType_$VALUES = null,
cpgci_ClickType_$callClinit = () => {
    cpgci_ClickType_$callClinit = $rt_eraseClinit(cpgci_ClickType);
    cpgci_ClickType__clinit_();
},
cpgci_ClickType_values = () => {
    cpgci_ClickType_$callClinit();
    return cpgci_ClickType_$VALUES.$clone0();
},
cpgci_ClickType__init_0 = ($this, var$1, var$2) => {
    cpgci_ClickType_$callClinit();
    jl_Enum__init_($this, var$1, var$2);
},
cpgci_ClickType__init_ = (var_0, var_1) => {
    let var_2 = new cpgci_ClickType();
    cpgci_ClickType__init_0(var_2, var_0, var_1);
    return var_2;
},
cpgci_ClickType_$values = () => {
    let var$1, var$2;
    cpgci_ClickType_$callClinit();
    var$1 = $rt_createArray(cpgci_ClickType, 5);
    var$2 = var$1.data;
    var$2[0] = cpgci_ClickType_MiddleClick;
    var$2[1] = cpgci_ClickType_LeftClick;
    var$2[2] = cpgci_ClickType_RightClick;
    var$2[3] = cpgci_ClickType_SideButton1;
    var$2[4] = cpgci_ClickType_SideButton2;
    return var$1;
},
cpgci_ClickType__clinit_ = () => {
    cpgci_ClickType_MiddleClick = cpgci_ClickType__init_($rt_s(4), 0);
    cpgci_ClickType_LeftClick = cpgci_ClickType__init_($rt_s(5), 1);
    cpgci_ClickType_RightClick = cpgci_ClickType__init_($rt_s(6), 2);
    cpgci_ClickType_SideButton1 = cpgci_ClickType__init_($rt_s(7), 3);
    cpgci_ClickType_SideButton2 = cpgci_ClickType__init_($rt_s(8), 4);
    cpgci_ClickType_$VALUES = cpgci_ClickType_$values();
},
ju_ConcurrentModificationException = $rt_classWithoutFields(jl_RuntimeException),
ju_ConcurrentModificationException__init_ = $this => {
    jl_RuntimeException__init_($this);
},
ju_ConcurrentModificationException__init_0 = () => {
    let var_0 = new ju_ConcurrentModificationException();
    ju_ConcurrentModificationException__init_(var_0);
    return var_0;
},
jlr_AnnotatedElement = $rt_classWithoutFields(0),
cpgv_GameObjectVisitor = $rt_classWithoutFields(),
cpgv_GameObjectVisitor__init_ = $this => {
    jl_Object__init_($this);
};
function cpgv_GameObjectRenderer() {
    let a = this; cpgv_GameObjectVisitor.call(a);
    a.$myGraphics = null;
    a.$myOrigin = null;
}
let cpgv_GameObjectRenderer__init_ = ($this, $theOrigin) => {
    cpgv_GameObjectVisitor__init_($this);
    $this.$myOrigin = cpgu_Vector2__init_1($theOrigin);
    $this.$myGraphics = null;
},
cpgv_GameObjectRenderer__init_0 = var_0 => {
    let var_1 = new cpgv_GameObjectRenderer();
    cpgv_GameObjectRenderer__init_(var_1, var_0);
    return var_1;
},
cpgv_GameObjectRenderer_updateGraphics = ($this, $theG) => {
    $this.$myGraphics = $theG;
},
cpgv_GameObjectRenderer_visit1 = ($this, $theEntity) => {
    let var$2, var$3, var$4, $edge, var$6, var$7, var$8, var$9, var$10, var$11;
    $this.$myGraphics.$setColor($rt_s(9));
    var$2 = ($theEntity.$getEdges()).data;
    var$3 = var$2.length;
    var$4 = 0;
    while (var$4 < var$3) {
        $edge = var$2[var$4];
        var$6 = $this.$myGraphics;
        var$7 = $this.$myOrigin;
        var$8 = var$7.$intX() + (cpgmr_RigidBodyEdge_getStart($edge)).$intX() | 0;
        var$7 = $this.$myOrigin;
        var$9 = var$7.$intY() + (cpgmr_RigidBodyEdge_getStart($edge)).$intY() | 0;
        var$7 = $this.$myOrigin;
        var$10 = var$7.$intX() + (cpgmr_RigidBodyEdge_getEnd($edge)).$intX() | 0;
        var$7 = $this.$myOrigin;
        var$11 = var$7.$intY() + (cpgmr_RigidBodyEdge_getEnd($edge)).$intY() | 0;
        var$6.$drawLine(var$8, var$9, var$10, var$11);
        var$4 = var$4 + 1 | 0;
    }
    $this.$myGraphics.$setColor($rt_s(9));
    $this.$myGraphics.$drawLine($this.$myOrigin.$intX() + ($theEntity.$getCenterOfMass()).$intX() | 0, $this.$myOrigin.$intY() + ($theEntity.$getCenterOfMass()).$intY() | 0, $this.$myOrigin.$intX() + ($theEntity.$getVertices()).data[0].$intX() | 0, $this.$myOrigin.$intY() + ($theEntity.$getVertices()).data[0].$intY() | 0);
    $this.$myGraphics.$fillOval(($this.$myOrigin.$intX() + ($theEntity.$getCenterOfMass()).$intX() | 0) - 2 | 0, ($this.$myOrigin.$intY() + ($theEntity.$getCenterOfMass()).$intY() | 0) - 2 | 0, 4, 4);
    return null;
},
cpgv_GameObjectRenderer_visit0 = ($this, $theEntity) => {
    let var$2, var$3, var$4, var$5, var$6, var$7, $orient;
    $this.$myGraphics.$setColor($rt_s(9));
    var$2 = $this.$myGraphics;
    var$3 = $this.$myOrigin;
    var$4 = (var$3.$intX() + ($theEntity.$getCenterOfMass()).$intX() | 0) - ($theEntity.$getRadius() | 0) | 0;
    var$3 = $this.$myOrigin;
    var$5 = (var$3.$intY() + ($theEntity.$getCenterOfMass()).$intY() | 0) - ($theEntity.$getRadius() | 0) | 0;
    var$6 = $theEntity.$getDiameter();
    var$7 = $theEntity.$getDiameter();
    var$2.$drawOval(var$4, var$5, var$6, var$7);
    $orient = $theEntity.$getOrientationVector();
    $this.$myGraphics.$drawLine($this.$myOrigin.$intX() + ($theEntity.$getCenterOfMass()).$intX() | 0, $this.$myOrigin.$intY() + ($theEntity.$getCenterOfMass()).$intY() | 0, $this.$myOrigin.$intX() + $orient.$intX() | 0, $this.$myOrigin.$intY() + $orient.$intY() | 0);
    return null;
},
cpgv_GameObjectRenderer_visit = ($this, var$1) => {
    return $this.$visit0(var$1);
},
cpgv_GameObjectRenderer_visit2 = ($this, var$1) => {
    return $this.$visit1(var$1);
};
function cpgmc_CollisionManager() {
    let a = this; jl_Object.call(a);
    a.$myWorld = null;
    a.$myCollisionType = null;
    a.$myCollisionResponses = null;
}
let cpgmc_CollisionManager_COE_RES = 0.0,
cpgmc_CollisionManager_$callClinit = () => {
    cpgmc_CollisionManager_$callClinit = $rt_eraseClinit(cpgmc_CollisionManager);
    cpgmc_CollisionManager__clinit_();
},
cpgmc_CollisionManager__init_ = ($this, $theWorld) => {
    cpgmc_CollisionManager_$callClinit();
    jl_Object__init_($this);
    $this.$myWorld = $theWorld;
    $this.$myCollisionType = ju_HashMap__init_2();
    $this.$myCollisionResponses = ju_ArrayList__init_0();
    $this.$myCollisionType.$put($rt_s(10), cpgmc_CollisionManager$_init_$lambda$_0_0__init_0($this));
    $this.$myCollisionType.$put($rt_s(11), cpgmc_CollisionManager$_init_$lambda$_0_1__init_0($this));
    $this.$myCollisionType.$put($rt_s(12), cpgmc_CollisionManager$_init_$lambda$_0_2__init_0($this));
    $this.$myCollisionType.$put($rt_s(13), cpgmc_CollisionManager$_init_$lambda$_0_3__init_0($this));
    $this.$myCollisionType.$put($rt_s(14), cpgmc_CollisionManager$_init_$lambda$_0_4__init_0($this));
    $this.$myCollisionType.$put($rt_s(15), cpgmc_CollisionManager$_init_$lambda$_0_5__init_0($this));
    $this.$myCollisionType.$put($rt_s(16), cpgmc_CollisionManager$_init_$lambda$_0_6__init_0($this));
    $this.$myCollisionType.$put($rt_s(17), cpgmc_CollisionManager$_init_$lambda$_0_7__init_0($this));
    $this.$myCollisionType.$put($rt_s(18), cpgmc_CollisionManager$_init_$lambda$_0_8__init_0($this));
},
cpgmc_CollisionManager__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager();
    cpgmc_CollisionManager__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager_getKeyPairing = ($this, $theA, $theB) => {
    let var$3, var$4, var$5;
    var$3 = $theA.$getName();
    var$4 = $theB.$getName();
    var$5 = jl_StringBuilder__init_();
    jl_StringBuilder_append(jl_StringBuilder_append0(jl_StringBuilder_append(var$5, var$3), 45), var$4);
    return jl_StringBuilder_toString(var$5);
},
cpgmc_CollisionManager_detectCollisions = ($this, $theA, $theIndex) => {
    let var$3, $staticObject, $i, $dynObject;
    var$3 = ($this.$myWorld.$getStaticObjects()).$iterator();
    while (var$3.$hasNext()) {
        $staticObject = var$3.$next();
        ($this.$myCollisionType.$get(cpgmc_CollisionManager_getKeyPairing($this, $theA, $staticObject))).$accept($theA, $staticObject);
    }
    $i = $theIndex + 1 | 0;
    while ($i < ($this.$myWorld.$getDynamicObjects()).$size()) {
        $dynObject = ($this.$myWorld.$getDynamicObjects()).$get0($i);
        ($this.$myCollisionType.$get(cpgmc_CollisionManager_getKeyPairing($this, $theA, $dynObject))).$accept($theA, $dynObject);
        $i = $i + 1 | 0;
    }
},
cpgmc_CollisionManager_handleCollisions = $this => {
    if ($this.$myCollisionResponses.$isEmpty())
        return;
    $this.$myCollisionResponses.$forEach(cpgmc_CollisionManager$handleCollisions$lambda$_3_0__init_0());
    $this.$myCollisionResponses.$clear();
},
cpgmc_CollisionManager_detectVerletObjectAndVerletObject = ($this, $theA, $theB) => {
    return;
},
cpgmc_CollisionManager_detectVerletObjectAndRigidCircle = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect1($theA, $theB);
    if ($manifold !== null) {
        $theB.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse1D__init_($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectVerletObjectAndRigidBody = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect0($theA, $theB);
    if ($manifold !== null) {
        $theB.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse1D__init_($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectRigidCircleAndVerletObject = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect1($theB, $theA);
    if ($manifold !== null) {
        (cpgmc_Manifold_getPenetration($manifold)).$mul((-1.0));
        (cpgmc_Manifold_getNormal($manifold)).$mul((-1.0));
        $theA.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse1D__init_2($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectRigidCircleAndRigidCircle = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect2($theA, $theB);
    if ($manifold !== null) {
        $theA.$incCollision();
        $theB.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse2D__init_($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectRigidCircleAndRigidBody = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect($theA, $theB);
    if ($manifold !== null) {
        $theA.$incCollision();
        $theB.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse2D__init_($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectRigidBodyAndVerletObject = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect0($theB, $theA);
    if ($manifold !== null) {
        (cpgmc_Manifold_getPenetration($manifold)).$mul((-1.0));
        (cpgmc_Manifold_getNormal($manifold)).$mul((-1.0));
        $theA.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse1D__init_2($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectRigidBodyAndRigidCircle = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect($theB, $theA);
    if ($manifold !== null) {
        (cpgmc_Manifold_getPenetration($manifold)).$mul((-1.0));
        (cpgmc_Manifold_getNormal($manifold)).$mul((-1.0));
        $theA.$incCollision();
        $theB.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse2D__init_($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_detectRigidBodyAndRigidBody = ($this, $theA, $theB) => {
    let $manifold;
    $manifold = cpgmc_CollisionDetection_detect3($theA, $theB);
    if ($manifold !== null) {
        $theA.$incCollision();
        $theB.$incCollision();
        $this.$myCollisionResponses.$add0(cpgmc_CollisionResponse2D__init_($theA, $theB, $manifold));
    }
},
cpgmc_CollisionManager_lambda$new$8 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectVerletObjectAndVerletObject($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$7 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectVerletObjectAndRigidCircle($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$6 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectVerletObjectAndRigidBody($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$5 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectRigidCircleAndVerletObject($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$4 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectRigidCircleAndRigidCircle($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$3 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectRigidCircleAndRigidBody($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$2 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectRigidBodyAndVerletObject($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$1 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectRigidBodyAndRigidCircle($this, $theA, $theB);
},
cpgmc_CollisionManager_lambda$new$0 = ($this, $theA, $theB) => {
    cpgmc_CollisionManager_detectRigidBodyAndRigidBody($this, $theA, $theB);
},
cpgmc_CollisionManager__clinit_ = () => {
    cpgmc_CollisionManager_COE_RES = 1.0;
};
function cpgu_Vector2() {
    let a = this; jl_Object.call(a);
    a.$myX = 0.0;
    a.$myY = 0.0;
}
let cpgu_Vector2__init_2 = ($this, $theX, $theY) => {
    jl_Object__init_($this);
    $this.$set0($theX, $theY);
},
cpgu_Vector2__init_ = (var_0, var_1) => {
    let var_2 = new cpgu_Vector2();
    cpgu_Vector2__init_2(var_2, var_0, var_1);
    return var_2;
},
cpgu_Vector2__init_4 = ($this, $theV) => {
    jl_Object__init_($this);
    if ($theV !== null)
        $this.$set0($theV.$getX(), $theV.$getY());
    else
        $this.$set0(0.0, 0.0);
},
cpgu_Vector2__init_1 = var_0 => {
    let var_1 = new cpgu_Vector2();
    cpgu_Vector2__init_4(var_1, var_0);
    return var_1;
},
cpgu_Vector2__init_3 = $this => {
    cpgu_Vector2__init_2($this, 0.0, 0.0);
},
cpgu_Vector2__init_0 = () => {
    let var_0 = new cpgu_Vector2();
    cpgu_Vector2__init_3(var_0);
    return var_0;
},
cpgu_Vector2_add = ($this, $theV) => {
    $this.$myX = $this.$myX + $theV.$myX;
    $this.$myY = $this.$myY + $theV.$myY;
},
cpgu_Vector2_sub = ($this, $theV) => {
    $this.$myX = $this.$myX - $theV.$myX;
    $this.$myY = $this.$myY - $theV.$myY;
},
cpgu_Vector2_mul = ($this, $theScalar) => {
    $this.$myX = $this.$myX * $theScalar;
    $this.$myY = $this.$myY * $theScalar;
},
cpgu_Vector2_div = ($this, $theScalar) => {
    $this.$myX = $this.$myX / $theScalar;
    $this.$myY = $this.$myY / $theScalar;
},
cpgu_Vector2_norm = $this => {
    $this.$div($this.$getMagnitude());
},
cpgu_Vector2_addNew = ($this, $theV) => {
    return cpgu_Vector2__init_($this.$myX + $theV.$myX, $this.$myY + $theV.$myY);
},
cpgu_Vector2_subNew = ($this, $theV) => {
    return cpgu_Vector2__init_($this.$myX - $theV.$myX, $this.$myY - $theV.$myY);
},
cpgu_Vector2_mulNew = ($this, $theScalar) => {
    return cpgu_Vector2__init_($this.$myX * $theScalar, $this.$myY * $theScalar);
},
cpgu_Vector2_divNew = ($this, $theScalar) => {
    return cpgu_Vector2__init_($this.$myX / $theScalar, $this.$myY / $theScalar);
},
cpgu_Vector2_normNew = $this => {
    return $this.$divNew($this.$getMagnitude());
},
cpgu_Vector2_perpNew = $this => {
    return cpgu_Vector2__init_($this.$myY,  -$this.$myX);
},
cpgu_Vector2_dotProduct = ($this, $theOther) => {
    return $this.$myX * $theOther.$myX + $this.$myY * $theOther.$myY;
},
cpgu_Vector2_crossProduct = ($this, $theOther) => {
    return $this.$myX * $theOther.$myY - $this.$myY * $theOther.$myX;
},
cpgu_Vector2_getMagnitude = $this => {
    return jl_Math_sqrt($this.$myX * $this.$myX + $this.$myY * $this.$myY);
},
cpgu_Vector2_getX = $this => {
    return $this.$myX;
},
cpgu_Vector2_getY = $this => {
    return $this.$myY;
},
cpgu_Vector2_intX = $this => {
    return jl_Math_round($this.$myX);
},
cpgu_Vector2_intY = $this => {
    return jl_Math_round($this.$myY);
},
cpgu_Vector2_setX = ($this, $theX) => {
    $this.$myX = $theX;
},
cpgu_Vector2_setY = ($this, $theY) => {
    $this.$myY = $theY;
},
cpgu_Vector2_set = ($this, $theX, $theY) => {
    $this.$setX($theX);
    $this.$setY($theY);
},
cpgu_Vector2_set0 = ($this, $theV) => {
    $this.$set0($theV.$getX(), $theV.$getY());
},
jl_ClassCastException = $rt_classWithoutFields(jl_RuntimeException);
function cpgmr_RigidCircle() {
    cpgmr_Rigid2D.call(this);
    this.$myRadius = 0.0;
}
let cpgmr_RigidCircle__init_ = ($this, $theOrigin, $theRadius, $theMass) => {
    cpgmr_Rigid2D__init_($this, $theOrigin, $theMass);
    if ($theRadius <= 0.0)
        $rt_throw(jl_IllegalArgumentException__init_($rt_s(19)));
    $this.$myRadius = $theRadius;
    $this.$myMoi = $this.$myRadius * $this.$myRadius * $this.$myMass / 2.0;
},
cpgmr_RigidCircle__init_0 = (var_0, var_1, var_2) => {
    let var_3 = new cpgmr_RigidCircle();
    cpgmr_RigidCircle__init_(var_3, var_0, var_1, var_2);
    return var_3;
},
cpgmr_RigidCircle_translate = ($this, $theTranslation) => {
    $this.$myPosition.$add($theTranslation);
    $this.$myOldPosition.$add($theTranslation);
},
cpgmr_RigidCircle_move = $this => {
    let $current;
    $this.$myCache.$set($this.$myPosition);
    $this.$myPosition.$add(($this.$myPosition.$subNew($this.$myOldPosition)).$addNew($this.$myAcceleration));
    $this.$myOldPosition.$set($this.$myCache);
    $current = $this.$myAngularPos;
    $this.$myAngularPos = $this.$myAngularPos + $this.$myAngularPos - $this.$myOldAngularPos + $this.$myAngularAccel;
    $this.$myOldAngularPos = $current;
},
cpgmr_RigidCircle_getRadius = $this => {
    return $this.$myRadius;
},
cpgmr_RigidCircle_getDiameter = $this => {
    return jl_Math_round($this.$myRadius * 2.0);
},
cpgmr_RigidCircle_getOrientationVector = $this => {
    $this.$myCache.$set0($this.$myRadius, 0.0);
    cpgu_VMath_rotate($this.$myCache, cpgu_Vector2__init_0(), $this.$myAngularPos);
    return $this.$myPosition.$addNew($this.$myCache);
},
cpgmr_RigidCircle_getName = $this => {
    return $rt_s(20);
},
cpgmr_RigidCircle_accept = ($this, $theV) => {
    return $theV.$visit2($this);
},
ju_AbstractCollection = $rt_classWithoutFields(),
ju_AbstractCollection__init_ = $this => {
    jl_Object__init_($this);
},
ju_AbstractCollection_isEmpty = $this => {
    return $this.$size() ? 0 : 1;
},
ju_SequencedCollection = $rt_classWithoutFields(0),
ju_List = $rt_classWithoutFields(0);
function ju_AbstractList() {
    ju_AbstractCollection.call(this);
    this.$modCount = 0;
}
let ju_AbstractList__init_ = $this => {
    ju_AbstractCollection__init_($this);
},
ju_AbstractList_iterator = $this => {
    return ju_AbstractList$1__init_0($this);
},
jl_Cloneable = $rt_classWithoutFields(0),
ju_RandomAccess = $rt_classWithoutFields(0);
function ju_ArrayList() {
    let a = this; ju_AbstractList.call(a);
    a.$array = null;
    a.$size0 = 0;
}
let ju_ArrayList__init_1 = $this => {
    ju_ArrayList__init_($this, 10);
},
ju_ArrayList__init_0 = () => {
    let var_0 = new ju_ArrayList();
    ju_ArrayList__init_1(var_0);
    return var_0;
},
ju_ArrayList__init_ = ($this, $initialCapacity) => {
    ju_AbstractList__init_($this);
    if ($initialCapacity >= 0) {
        $this.$array = $rt_createArray(jl_Object, $initialCapacity);
        return;
    }
    $rt_throw(jl_IllegalArgumentException__init_0());
},
ju_ArrayList__init_2 = var_0 => {
    let var_1 = new ju_ArrayList();
    ju_ArrayList__init_(var_1, var_0);
    return var_1;
},
ju_ArrayList_ensureCapacity = ($this, $minCapacity) => {
    let $newLength;
    if ($this.$array.data.length < $minCapacity) {
        $newLength = $this.$array.data.length >= 1073741823 ? 2147483647 : jl_Math_max($minCapacity, jl_Math_max($this.$array.data.length * 2 | 0, 5));
        $this.$array = ju_Arrays_copyOf0($this.$array, $newLength);
    }
},
ju_ArrayList_get = ($this, $index) => {
    ju_ArrayList_checkIndex($this, $index);
    return $this.$array.data[$index];
},
ju_ArrayList_size = $this => {
    return $this.$size0;
},
ju_ArrayList_add = ($this, $element) => {
    let var$2, var$3;
    $this.$ensureCapacity($this.$size0 + 1 | 0);
    var$2 = $this.$array.data;
    var$3 = $this.$size0;
    $this.$size0 = var$3 + 1 | 0;
    var$2[var$3] = $element;
    $this.$modCount = $this.$modCount + 1 | 0;
    return 1;
},
ju_ArrayList_clear = $this => {
    ju_Arrays_fill($this.$array, 0, $this.$size0, null);
    $this.$size0 = 0;
    $this.$modCount = $this.$modCount + 1 | 0;
},
ju_ArrayList_checkIndex = ($this, $index) => {
    if ($index >= 0 && $index < $this.$size0)
        return;
    $rt_throw(jl_IndexOutOfBoundsException__init_0());
},
ju_ArrayList_forEach = ($this, $action) => {
    let $i;
    $i = 0;
    while ($i < $this.$size0) {
        $action.$accept0($this.$array.data[$i]);
        $i = $i + 1 | 0;
    }
};
function cpgmc_CollisionResponse1D() {
    let a = this; cpgmcr_CollisionResponse.call(a);
    a.$myVO = null;
    a.$myRB = null;
    a.$myManifold0 = null;
    a.$isReversed = 0;
}
let cpgmc_CollisionResponse1D__init_1 = ($this, $theA, $theB, $theManifold) => {
    cpgmcr_CollisionResponse__init_($this);
    $this.$myVO = $theA;
    $this.$myRB = $theB;
    $this.$myManifold0 = $theManifold;
    $this.$isReversed = 0;
},
cpgmc_CollisionResponse1D__init_ = (var_0, var_1, var_2) => {
    let var_3 = new cpgmc_CollisionResponse1D();
    cpgmc_CollisionResponse1D__init_1(var_3, var_0, var_1, var_2);
    return var_3;
},
cpgmc_CollisionResponse1D__init_0 = ($this, $theA, $theB, $theManifold) => {
    cpgmcr_CollisionResponse__init_($this);
    $this.$myVO = $theB;
    $this.$myRB = $theA;
    $this.$myManifold0 = $theManifold;
    $this.$isReversed = 1;
},
cpgmc_CollisionResponse1D__init_2 = (var_0, var_1, var_2) => {
    let var_3 = new cpgmc_CollisionResponse1D();
    cpgmc_CollisionResponse1D__init_0(var_3, var_0, var_1, var_2);
    return var_3;
},
cpgmc_CollisionResponse1D_handleResponse = $this => {
    cpgmc_CollisionResponse1D_get1DImpulse($this);
    (cpgmc_Manifold_getPoint($this.$myManifold0)).$subNew($this.$myRB.$getCenterOfMass());
},
cpgmc_CollisionResponse1D_get1DImpulse = $this => {
    let $sumInvMassA, $normalSquared, $relVelA, $relVelB, $relNorm, $denom;
    $sumInvMassA = 1.0 / $this.$myVO.$getMass() + (!$this.$myRB.$hasDynamics() ? 0.0 : 1.0 / $this.$myRB.$getSplitMass());
    $normalSquared = (cpgmc_Manifold_getNormal($this.$myManifold0)).$dotProduct(cpgmc_Manifold_getNormal($this.$myManifold0));
    $relVelA = $this.$myVO.$getVelocity();
    $relVelB = ($this.$myRB.$getLinearVelocity()).$addNew($this.$myRB.$getLinearAngularVelocity(cpgmc_Manifold_getPoint($this.$myManifold0)));
    $relNorm = ($relVelB.$subNew($relVelA)).$dotProduct(cpgmc_Manifold_getNormal($this.$myManifold0));
    $denom = $normalSquared * $sumInvMassA;
    cpgmc_CollisionManager_$callClinit();
    return  -(1.0 + cpgmc_CollisionManager_COE_RES) * $relNorm / $denom;
},
juf_BiConsumer = $rt_classWithoutFields(0);
function cpgv_DrawCanvas() {
    let a = this; jl_Object.call(a);
    a.$myWidth = 0;
    a.$myHeight = 0;
    a.$myCanvas = null;
    a.$context = null;
}
let cpgv_DrawCanvas__init_ = ($this, $theRatio, $theScale) => {
    let $document, var$4, var$5;
    jl_Object__init_($this);
    $this.$myWidth = $rt_imul($theRatio.$intX(), $theScale);
    $this.$myHeight = $rt_imul($theRatio.$intY(), $theScale);
    $document = window.document;
    $this.$myCanvas = $document.createElement("canvas");
    var$4 = $this.$myCanvas;
    var$5 = $this.$myWidth;
    var$4.width = var$5;
    var$4 = $this.$myCanvas;
    var$5 = $this.$myHeight;
    var$4.height = var$5;
    var$5 = $document.body;
    var$4 = $this.$myCanvas;
    var$5.appendChild(var$4);
    $this.$context = $this.$myCanvas.getContext("2d");
},
cpgv_DrawCanvas__init_0 = (var_0, var_1) => {
    let var_2 = new cpgv_DrawCanvas();
    cpgv_DrawCanvas__init_(var_2, var_0, var_1);
    return var_2;
},
cpgv_DrawCanvas_setColor = ($this, $theColor) => {
    let var$2, var$3;
    var$2 = $this.$context;
    var$3 = $rt_ustr($theColor);
    var$2.fillStyle = var$3;
    var$2 = $this.$context;
    var$3 = $rt_ustr($theColor);
    var$2.strokeStyle = var$3;
},
cpgv_DrawCanvas_clearRect = ($this, $x, $y, $width, $height) => {
    let var$5, var$6, var$7, var$8, var$9;
    var$5 = $this.$context;
    var$6 = $x;
    var$7 = $y;
    var$8 = $width;
    var$9 = $height;
    var$5.clearRect(var$6, var$7, var$8, var$9);
},
cpgv_DrawCanvas_drawLine = ($this, $x1, $y1, $x2, $y2) => {
    let var$5, var$6, var$7, var$8;
    $this.$context.beginPath();
    var$5 = $this.$context;
    var$6 = $x1;
    var$7 = $y1;
    var$5.moveTo(var$6, var$7);
    var$8 = $this.$context;
    var$6 = $x2;
    var$7 = $y2;
    var$8.lineTo(var$6, var$7);
    $this.$context.stroke();
},
cpgv_DrawCanvas_drawOval = ($this, $x, $y, $width, $height) => {
    let var$5, var$6, var$7, var$8, var$9;
    $this.$context.save();
    $this.$context.beginPath();
    var$5 = $this.$context;
    var$6 = $x;
    var$7 = $width / 2.0;
    var$6 = var$6 + var$7;
    var$8 = $y;
    var$9 = $height / 2.0;
    var$8 = var$8 + var$9;
    var$5.translate(var$6, var$8);
    $this.$context.scale(var$7, var$9);
    $this.$context.arc(0.0, 0.0, 1.0, 0.0, 6.283185307179586, !!0);
    $this.$context.restore();
    $this.$context.stroke();
},
cpgv_DrawCanvas_fillOval = ($this, $x, $y, $width, $height) => {
    let var$5, var$6, var$7, var$8, var$9;
    $this.$context.save();
    $this.$context.beginPath();
    var$5 = $this.$context;
    var$6 = $x;
    var$7 = $width / 2.0;
    var$6 = var$6 + var$7;
    var$8 = $y;
    var$9 = $height / 2.0;
    var$8 = var$8 + var$9;
    var$5.translate(var$6, var$8);
    $this.$context.scale(var$7, var$9);
    $this.$context.arc(0.0, 0.0, 1.0, 0.0, 6.283185307179586, !!0);
    $this.$context.restore();
    $this.$context.fill();
},
cpgv_DrawCanvas_getCanvas = $this => {
    return $this.$myCanvas;
},
cpgv_DrawCanvas_getWidth = $this => {
    return $this.$myWidth;
},
cpgv_DrawCanvas_getHeight = $this => {
    return $this.$myHeight;
};
function jl_String() {
    jl_Object.call(this);
    this.$hashCode1 = 0;
}
let jl_String_EMPTY_CHARS = null,
jl_String_EMPTY = null,
jl_String_CASE_INSENSITIVE_ORDER = null,
jl_String_$callClinit = () => {
    jl_String_$callClinit = $rt_eraseClinit(jl_String);
    jl_String__clinit_();
},
jl_String__init_ = $this => {
    jl_String_$callClinit();
    jl_Object__init_($this);
    $this.$nativeString = "";
},
jl_String__init_4 = () => {
    let var_0 = new jl_String();
    jl_String__init_(var_0);
    return var_0;
},
jl_String__init_0 = ($this, $characters) => {
    let var$2;
    jl_String_$callClinit();
    var$2 = $characters.data;
    jl_Object__init_($this);
    $this.$nativeString = $rt_charArrayToString($characters.data, 0, var$2.length);
},
jl_String__init_3 = var_0 => {
    let var_1 = new jl_String();
    jl_String__init_0(var_1, var_0);
    return var_1;
},
jl_String__init_1 = (var$0, var$1) => {
    var$0.$nativeString = var$1;
},
jl_String__init_5 = var_0 => {
    let var_1 = new jl_String();
    jl_String__init_1(var_1, var_0);
    return var_1;
},
jl_String__init_2 = (var$0, var$1, $offset, $count) => {
    let var$4;
    jl_String_$callClinit();
    var$4 = var$1.data;
    jl_Object__init_(var$0);
    ju_Objects_checkFromIndexSize($offset, $count, var$4.length);
    var$0.$nativeString = $rt_charArrayToString(var$1.data, $offset, $count);
},
jl_String__init_6 = (var_0, var_1, var_2) => {
    let var_3 = new jl_String();
    jl_String__init_2(var_3, var_0, var_1, var_2);
    return var_3;
},
jl_String_charAt = ($this, $index) => {
    if ($index >= 0 && $index < $this.$nativeString.length)
        return $this.$nativeString.charCodeAt($index);
    $rt_throw(jl_StringIndexOutOfBoundsException__init_0());
},
jl_String_length = $this => {
    return $this.$nativeString.length;
},
jl_String_isEmpty = $this => {
    return $this.$nativeString.length ? 0 : 1;
},
jl_String_toString = $this => {
    return $this;
},
jl_String_equals = ($this, $other) => {
    let $str;
    if ($this === $other)
        return 1;
    if (!($other instanceof jl_String))
        return 0;
    $str = $other;
    return $this.$nativeString !== $str.$nativeString ? 0 : 1;
},
jl_String_hashCode = $this => {
    let $i;
    a: {
        if (!$this.$hashCode1) {
            $i = 0;
            while (true) {
                if ($i >= $this.$nativeString.length)
                    break a;
                $this.$hashCode1 = (31 * $this.$hashCode1 | 0) + $this.$nativeString.charCodeAt($i) | 0;
                $i = $i + 1 | 0;
            }
        }
    }
    return $this.$hashCode1;
},
jl_String__clinit_ = () => {
    jl_String_EMPTY_CHARS = $rt_createCharArray(0);
    jl_String_EMPTY = jl_String__init_4();
    jl_String_CASE_INSENSITIVE_ORDER = jl_String$_clinit_$lambda$_115_0__init_0();
},
cpgmr_RegularPolygon = $rt_classWithoutFields(cpgmr_RigidBody),
cpgmr_RegularPolygon__init_ = ($this, $theOrigin, $theNumSides, $theSideLength, $theMass) => {
    cpgmr_RigidBody__init_($this, $theOrigin, $theMass, cpgmr_RegularPolygon_generateRegularPolygon($theOrigin, $theNumSides, $theSideLength));
},
cpgmr_RegularPolygon__init_0 = (var_0, var_1, var_2, var_3) => {
    let var_4 = new cpgmr_RegularPolygon();
    cpgmr_RegularPolygon__init_(var_4, var_0, var_1, var_2, var_3);
    return var_4;
},
cpgmr_RegularPolygon_accept = ($this, $theV) => {
    return $theV.$visit($this);
},
cpgmr_RegularPolygon_generateRegularPolygon = ($theOrigin, $theNumSides, $theLength) => {
    let $vertices, $ang, $radius, $angleIncrement, $i, var$9, $angle, $x, $y;
    $vertices = $rt_createArray(cpgu_Vector2, $theNumSides);
    $ang = 3.141592653589793 / $theNumSides;
    $radius = $theLength / (2.0 * jl_Math_sin($ang));
    $angleIncrement = 2.0 * $ang;
    $i = 0;
    while ($i < $theNumSides) {
        var$9 = $vertices.data;
        $angle = $i * $angleIncrement;
        $x = $theOrigin.$getX() + $radius * jl_Math_cos($angle);
        $y = $theOrigin.$getY() + $radius * jl_Math_sin($angle);
        var$9[$i] = cpgu_Vector2__init_($x, $y);
        $i = $i + 1 | 0;
    }
    return $vertices;
};
function cpgv_GameplayScreen() {
    let a = this; cpgv_GameScreen.call(a);
    a.$myGameplayController = null;
    a.$myRenderer = null;
}
let cpgv_GameplayScreen__init_ = ($this, $theName) => {
    cpgv_GameScreen__init_($this, $theName);
    $this.$myGameplayController = cpgc_GameplayController__init_0(cpgci_InputController__init_0(cpgv_GameScreen_myCanvas, cpgv_GameScreen_myOrigin));
    $this.$myRenderer = cpgv_GameObjectRenderer__init_0(cpgv_GameScreen_myOrigin);
    $this.$myRenderer.$updateGraphics(cpgv_GameScreen_myCanvas);
},
cpgv_GameplayScreen__init_0 = var_0 => {
    let var_1 = new cpgv_GameplayScreen();
    cpgv_GameplayScreen__init_(var_1, var_0);
    return var_1;
},
cpgv_GameplayScreen_tick = $this => {
    $this.$myGameplayController.$update();
},
cpgv_GameplayScreen_render = $this => {
    let $gw;
    $gw = $this.$myGameplayController.$getGameWorld();
    ($gw.$getDynamicObjects()).$forEach(cpgv_GameplayScreen$render$lambda$_2_0__init_0($this));
    ($gw.$getStaticObjects()).$forEach(cpgv_GameplayScreen$render$lambda$_2_1__init_0($this));
},
cpgv_GameplayScreen_lambda$render$1 = ($this, $b) => {
    $b.$accept1($this.$myRenderer);
},
cpgv_GameplayScreen_lambda$render$0 = ($this, $go) => {
    $go.$accept1($this.$myRenderer);
},
jl_NegativeArraySizeException = $rt_classWithoutFields(jl_RuntimeException),
jl_NegativeArraySizeException__init_ = $this => {
    jl_RuntimeException__init_($this);
},
jl_NegativeArraySizeException__init_0 = () => {
    let var_0 = new jl_NegativeArraySizeException();
    jl_NegativeArraySizeException__init_(var_0);
    return var_0;
},
ju_Map$Entry = $rt_classWithoutFields(0);
function cpgm_GameWorld() {
    let a = this; jl_Object.call(a);
    a.$myDynamicObjects = null;
    a.$myStaticObjects = null;
}
let cpgm_GameWorld_GRAVITY = null,
cpgm_GameWorld_SCREEN_BOUNDARY = null,
cpgm_GameWorld_$callClinit = () => {
    cpgm_GameWorld_$callClinit = $rt_eraseClinit(cpgm_GameWorld);
    cpgm_GameWorld__clinit_();
},
cpgm_GameWorld__init_ = $this => {
    cpgm_GameWorld_$callClinit();
    jl_Object__init_($this);
    $this.$myDynamicObjects = ju_ArrayList__init_0();
    $this.$myStaticObjects = ju_ArrayList__init_0();
},
cpgm_GameWorld__init_0 = () => {
    let var_0 = new cpgm_GameWorld();
    cpgm_GameWorld__init_(var_0);
    return var_0;
},
cpgm_GameWorld_addDynamicObject = ($this, $theObject) => {
    $this.$myDynamicObjects.$add0($theObject);
},
cpgm_GameWorld_addStaticObject = ($this, $theObject) => {
    $this.$myStaticObjects.$add0($theObject);
},
cpgm_GameWorld_getDynamicObjects = $this => {
    return $this.$myDynamicObjects;
},
cpgm_GameWorld_getStaticObjects = $this => {
    return $this.$myStaticObjects;
},
cpgm_GameWorld__clinit_ = () => {
    cpgm_GameWorld_GRAVITY = cpgu_Vector2__init_(0.0, 0.0);
    cpgm_GameWorld_SCREEN_BOUNDARY = cpgu_Vector2__init_(0.0, 0.0);
},
jl_IncompatibleClassChangeError = $rt_classWithoutFields(jl_LinkageError),
jl_IncompatibleClassChangeError__init_ = $this => {
    jl_LinkageError__init_($this);
},
jl_IncompatibleClassChangeError__init_0 = () => {
    let var_0 = new jl_IncompatibleClassChangeError();
    jl_IncompatibleClassChangeError__init_(var_0);
    return var_0;
};
function cpgci_InputController() {
    jl_Object.call(this);
    this.$mouse = null;
}
let cpgci_InputController__init_ = ($this, $canvas, $theOrigin) => {
    jl_Object__init_($this);
    $this.$mouse = cpgci_Mouse__init_0(cpgu_Vector2__init_(($canvas.$getCanvas()).getBoundingClientRect().left, ($canvas.$getCanvas()).getBoundingClientRect().top), $theOrigin);
    $this.$mouse.$addListenersCanvas($canvas);
},
cpgci_InputController__init_0 = (var_0, var_1) => {
    let var_2 = new cpgci_InputController();
    cpgci_InputController__init_(var_2, var_0, var_1);
    return var_2;
},
cpgci_InputController_getMouse = $this => {
    return $this.$mouse;
},
cpgci_InputController_getMousePos = $this => {
    return $this.$mouse.$getPos();
};
function cpgmc_CollisionResponse2D() {
    let a = this; cpgmcr_CollisionResponse.call(a);
    a.$myA = null;
    a.$myB = null;
    a.$myManifold = null;
}
let cpgmc_CollisionResponse2D__init_0 = ($this, $theA, $theB, $theManifold) => {
    cpgmcr_CollisionResponse__init_($this);
    $this.$myA = $theA;
    $this.$myB = $theB;
    $this.$myManifold = $theManifold;
    (cpgmc_Manifold_getNormal($this.$myManifold)).$norm();
},
cpgmc_CollisionResponse2D__init_ = (var_0, var_1, var_2) => {
    let var_3 = new cpgmc_CollisionResponse2D();
    cpgmc_CollisionResponse2D__init_0(var_3, var_0, var_1, var_2);
    return var_3;
},
cpgmc_CollisionResponse2D_handleResponse = $this => {
    let $sumInvMassA, $normalSquared, $relVelA, $relVelB, $relNorm, $rA, $rB, $angA, $angB, $denom, $impulse, $half;
    $sumInvMassA = 1.0 / $this.$myA.$getSplitMass() + (!$this.$myB.$hasDynamics() ? 0.0 : 1.0 / $this.$myB.$getSplitMass());
    $normalSquared = (cpgmc_Manifold_getNormal($this.$myManifold)).$dotProduct(cpgmc_Manifold_getNormal($this.$myManifold));
    $relVelA = ($this.$myA.$getLinearVelocity()).$addNew($this.$myA.$getLinearAngularVelocity(cpgmc_Manifold_getPoint($this.$myManifold)));
    $relVelB = ($this.$myB.$getLinearVelocity()).$addNew($this.$myB.$getLinearAngularVelocity(cpgmc_Manifold_getPoint($this.$myManifold)));
    $relNorm = ($relVelB.$subNew($relVelA)).$dotProduct(cpgmc_Manifold_getNormal($this.$myManifold));
    $rA = (cpgmc_Manifold_getPoint($this.$myManifold)).$subNew($this.$myA.$getCenterOfMass());
    $rB = (cpgmc_Manifold_getPoint($this.$myManifold)).$subNew($this.$myB.$getCenterOfMass());
    $angA = (cpgmc_Manifold_getNormal($this.$myManifold)).$crossProduct($rA);
    $angB = (cpgmc_Manifold_getNormal($this.$myManifold)).$crossProduct($rB);
    $denom = $normalSquared * $sumInvMassA + $angA * $angA / $this.$myA.$getMoi() + (!$this.$myB.$hasDynamics() ? 0.0 : $angB * $angB / $this.$myB.$getMoi());
    cpgmc_CollisionManager_$callClinit();
    $impulse =  -(1.0 + cpgmc_CollisionManager_COE_RES) * $relNorm / $denom;
    $this.$myA.$applyImpulse( -$impulse, cpgmc_Manifold_getNormal($this.$myManifold), $rA);
    if (!$this.$myB.$hasDynamics())
        $this.$myA.$translate(cpgmc_Manifold_getPenetration($this.$myManifold));
    else {
        if (cpgmc_Manifold_getPoint2($this.$myManifold) !== null)
            $rB = (cpgmc_Manifold_getPoint2($this.$myManifold)).$subNew($this.$myB.$getCenterOfMass());
        $this.$myB.$applyImpulse($impulse, cpgmc_Manifold_getNormal($this.$myManifold), $rB);
        $half = (cpgmc_Manifold_getPenetration($this.$myManifold)).$divNew(2.0);
        $this.$myA.$translate($half);
        $half.$mul((-1.0));
        $this.$myB.$translate($half);
    }
},
ju_Iterator = $rt_classWithoutFields(0);
function ju_AbstractList$1() {
    let a = this; jl_Object.call(a);
    a.$index = 0;
    a.$modCount1 = 0;
    a.$size1 = 0;
    a.$removeIndex = 0;
    a.$this$0 = null;
}
let ju_AbstractList$1__init_ = ($this, $this$0) => {
    $this.$this$0 = $this$0;
    jl_Object__init_($this);
    $this.$modCount1 = $this.$this$0.$modCount;
    $this.$size1 = $this.$this$0.$size();
    $this.$removeIndex = (-1);
},
ju_AbstractList$1__init_0 = var_0 => {
    let var_1 = new ju_AbstractList$1();
    ju_AbstractList$1__init_(var_1, var_0);
    return var_1;
},
ju_AbstractList$1_hasNext = $this => {
    return $this.$index >= $this.$size1 ? 0 : 1;
},
ju_AbstractList$1_next = $this => {
    let var$1, var$2;
    ju_AbstractList$1_checkConcurrentModification($this);
    $this.$removeIndex = $this.$index;
    var$1 = $this.$this$0;
    var$2 = $this.$index;
    $this.$index = var$2 + 1 | 0;
    return var$1.$get0(var$2);
},
ju_AbstractList$1_checkConcurrentModification = $this => {
    if ($this.$modCount1 >= $this.$this$0.$modCount)
        return;
    $rt_throw(ju_ConcurrentModificationException__init_0());
};
function cpgmc_CollisionManager$_init_$lambda$_0_3() {
    jl_Object.call(this);
    this.$_07 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_3__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_07 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_3__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_3();
    cpgmc_CollisionManager$_init_$lambda$_0_3__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_3_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_3_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_3_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$3(var$0.$_07, var$1, var$2);
};
function cpgmc_CollisionManager$_init_$lambda$_0_4() {
    jl_Object.call(this);
    this.$_0 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_4__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_0 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_4__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_4();
    cpgmc_CollisionManager$_init_$lambda$_0_4__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_4_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_4_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_4_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$4(var$0.$_0, var$1, var$2);
};
function cpgmc_CollisionManager$_init_$lambda$_0_5() {
    jl_Object.call(this);
    this.$_014 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_5__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_014 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_5__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_5();
    cpgmc_CollisionManager$_init_$lambda$_0_5__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_5_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_5_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_5_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$5(var$0.$_014, var$1, var$2);
};
function cpgmc_CollisionManager$_init_$lambda$_0_6() {
    jl_Object.call(this);
    this.$_06 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_6__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_06 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_6__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_6();
    cpgmc_CollisionManager$_init_$lambda$_0_6__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_6_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_6_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_6_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$6(var$0.$_06, var$1, var$2);
},
jlr_Array = $rt_classWithoutFields(),
jlr_Array_newInstance = ($componentType, $length) => {
    if ($componentType === null)
        $rt_throw(jl_NullPointerException__init_0());
    if ($componentType === $rt_cls($rt_voidcls))
        $rt_throw(jl_IllegalArgumentException__init_0());
    if ($length < 0)
        $rt_throw(jl_NegativeArraySizeException__init_0());
    return jlr_Array_newInstanceImpl(jl_Class_getPlatformClass($componentType), $length);
},
jlr_Array_newInstanceImpl = (var$1, var$2) => {
    if (var$1.$meta.primitive) {
        switch (var$1) {
        }
        ;
    }
    return $rt_createArray(var$1, var$2);
};
function cpgmc_CollisionManager$_init_$lambda$_0_0() {
    jl_Object.call(this);
    this.$_01 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_0__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_01 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_0__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_0();
    cpgmc_CollisionManager$_init_$lambda$_0_0__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_0_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_0_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_0_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$0(var$0.$_01, var$1, var$2);
};
function cpgmc_CollisionManager$_init_$lambda$_0_1() {
    jl_Object.call(this);
    this.$_09 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_1__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_09 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_1__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_1();
    cpgmc_CollisionManager$_init_$lambda$_0_1__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_1_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_1_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_1_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$1(var$0.$_09, var$1, var$2);
},
cpgu_VMath = $rt_classWithoutFields(),
cpgu_VMath_findMidpoint = ($theA, $theB) => {
    return cpgu_Vector2__init_(($theA.$getX() + $theB.$getX()) / 2.0, ($theA.$getY() + $theB.$getY()) / 2.0);
},
cpgu_VMath_project = ($theStart, $theEnd, $thePoint) => {
    let $startToEnd, $startToPoint, $t;
    $startToEnd = $theEnd.$subNew($theStart);
    $startToPoint = $thePoint.$subNew($theStart);
    $t = $startToPoint.$dotProduct($startToEnd) / $startToEnd.$dotProduct($startToEnd);
    if ($t >= 0.0 && $t <= 1.0) {
        $startToEnd.$mul($t);
        return $theStart.$addNew($startToEnd);
    }
    return null;
},
cpgu_VMath_rotate = ($theVector, $theOrigin, $theRadians) => {
    let var$4, var$5;
    $theVector.$sub($theOrigin);
    var$4 = $theVector.$getX() * jl_Math_cos($theRadians) + $theVector.$getY() * jl_Math_sin($theRadians);
    var$5 = $theVector.$getY() * jl_Math_cos($theRadians) - $theVector.$getX() * jl_Math_sin($theRadians);
    $theVector.$set0(var$4, var$5);
    $theVector.$add($theOrigin);
},
cpgu_VMath_findArea = $theVertices => {
    let $area, $i, var$4, var$5, var$6;
    $area = 0.0;
    $i = 0;
    while (true) {
        var$4 = $theVertices.data;
        var$5 = var$4.length;
        if ($i >= var$5)
            break;
        var$6 = var$4[$i];
        $i = $i + 1 | 0;
        $area = $area + var$6.$crossProduct(var$4[$i % var$5 | 0]);
    }
    return $area / 2.0;
},
cpgu_VMath_findCentroid = $theVertices => {
    let $centroid, $c, $i, var$5, var$6, $i_0, $nextVertex, $a;
    $centroid = cpgu_Vector2__init_0();
    $c = 1.0 / (6.0 * cpgu_VMath_findArea($theVertices));
    $i = 0;
    while (true) {
        var$5 = $theVertices.data;
        var$6 = var$5.length;
        if ($i >= var$6)
            break;
        $i_0 = $i + 1 | 0;
        $nextVertex = var$5[$i_0 % var$6 | 0];
        $a = var$5[$i].$crossProduct($nextVertex);
        $centroid.$add((var$5[$i].$addNew($nextVertex)).$mulNew($a));
        $i = $i_0;
    }
    $centroid.$mul($c);
    return $centroid;
},
cpgu_VMath_findMomentOfInertia = ($theVertices, $theCenterOfMass, $theMass) => {
    let $inertia, $i, var$6, var$7, $i_0, $nextVertex, $a, var$11, var$12, $b;
    $inertia = 0.0;
    $i = 0;
    while (true) {
        var$6 = $theVertices.data;
        var$7 = var$6.length;
        if ($i >= var$7)
            break;
        $i_0 = $i + 1 | 0;
        $nextVertex = var$6[$i_0 % var$7 | 0];
        $a = var$6[$i].$crossProduct($nextVertex);
        var$11 = var$6[$i].$dotProduct(var$6[$i]);
        var$12 = var$6[$i];
        var$11 = var$11 + var$12.$dotProduct($nextVertex);
        $b = var$11 + $nextVertex.$dotProduct($nextVertex);
        $inertia = $inertia + $a * $b;
        $i = $i_0;
    }
    var$11 = $inertia * $theMass / (12.0 * cpgu_VMath_findArea($theVertices));
    return var$11 - $theMass * $theCenterOfMass.$dotProduct($theCenterOfMass);
},
cpgu_VMath_findSupportVector = ($theVectors, $theDirection) => {
    let var$3, var$4, $bestVector, $bestProjection, var$7, $vector, $projection;
    var$3 = $theVectors.data;
    var$4 = var$3.length;
    if (!var$4)
        $rt_throw(jl_IllegalArgumentException__init_($rt_s(21)));
    $bestVector = var$3[0];
    $bestProjection = var$3[0].$dotProduct($theDirection);
    var$7 = 0;
    while (var$7 < var$4) {
        $vector = var$3[var$7];
        $projection = $vector.$dotProduct($theDirection);
        if ($projection > $bestProjection) {
            $bestProjection = $projection;
            $bestVector = $vector;
        }
        var$7 = var$7 + 1 | 0;
    }
    return $bestVector;
},
cpgu_VMath_isConvex = $theEdges => {
    let var$2, $convexity, $i, var$5;
    var$2 = $theEdges.data;
    $convexity = jl_Math_signum((cpgmr_RigidBodyEdge_getEdge(var$2[0])).$crossProduct(cpgmr_RigidBodyEdge_getEdge(var$2[1]))) | 0;
    $i = 1;
    while ($i < (var$2.length - 1 | 0)) {
        var$5 = cpgmr_RigidBodyEdge_getEdge(var$2[$i]);
        $i = $i + 1 | 0;
        if ($rt_imul(jl_Math_signum(var$5.$crossProduct(cpgmr_RigidBodyEdge_getEdge(var$2[$i]))) | 0, $convexity) < 0)
            return 0;
    }
    return 1;
},
cpgu_VMath_findAxisOfPenetration = ($theA, $theB) => {
    let $point, $normal, $bestProjection, $bestIndex, $index, var$8, var$9, var$10, $e, $edgeNormalOfA, $support, $projection, var$15;
    $point = cpgu_Vector2__init_0();
    $normal = cpgu_Vector2__init_0();
    $bestProjection = (-Infinity);
    $bestIndex = 0;
    $index = 0;
    var$8 = ($theA.$getEdges()).data;
    var$9 = var$8.length;
    var$10 = 0;
    while (var$10 < var$9) {
        $e = var$8[var$10];
        $edgeNormalOfA = cpgmr_RigidBodyEdge_getPerp($e);
        $support = cpgu_VMath_findSupportVector($theB.$getVertices(), $edgeNormalOfA.$mulNew((-1.0)));
        $projection = ($support.$subNew(cpgmr_RigidBodyEdge_getStart($e))).$dotProduct($edgeNormalOfA.$normNew());
        if ($projection > $bestProjection) {
            $point.$set($support);
            $normal.$set($edgeNormalOfA);
            $bestIndex = $index;
            $bestProjection = $projection;
        }
        $index = $index + 1 | 0;
        var$10 = var$10 + 1 | 0;
    }
    if ($bestProjection > 0.0)
        return $rt_createArray(cpgu_Vector2, 0);
    var$8 = $rt_createArray(cpgu_Vector2, 3);
    var$15 = var$8.data;
    var$15[0] = $point;
    var$15[1] = $normal;
    var$15[2] = cpgu_Vector2__init_($bestIndex, 0.0);
    return var$8;
};
function cpgmc_CollisionManager$_init_$lambda$_0_2() {
    jl_Object.call(this);
    this.$_00 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_2__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_00 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_2__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_2();
    cpgmc_CollisionManager$_init_$lambda$_0_2__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_2_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_2_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_2_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$2(var$0.$_00, var$1, var$2);
},
jl_NullPointerException = $rt_classWithoutFields(jl_RuntimeException),
jl_NullPointerException__init_ = $this => {
    jl_RuntimeException__init_($this);
},
jl_NullPointerException__init_0 = () => {
    let var_0 = new jl_NullPointerException();
    jl_NullPointerException__init_(var_0);
    return var_0;
},
cpgci_Mouse$1 = $rt_classWithoutFields(),
cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType = null,
cpgci_Mouse$1_$callClinit = () => {
    cpgci_Mouse$1_$callClinit = $rt_eraseClinit(cpgci_Mouse$1);
    cpgci_Mouse$1__clinit_();
},
cpgci_Mouse$1__clinit_ = () => {
    cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType = $rt_createIntArray((cpgci_ClickType_values()).data.length);
    cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType.data[jl_Enum_ordinal(cpgci_ClickType_LeftClick)] = 1;
    cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType.data[jl_Enum_ordinal(cpgci_ClickType_RightClick)] = 2;
    cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType.data[jl_Enum_ordinal(cpgci_ClickType_MiddleClick)] = 3;
    cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType.data[jl_Enum_ordinal(cpgci_ClickType_SideButton1)] = 4;
    cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType.data[jl_Enum_ordinal(cpgci_ClickType_SideButton2)] = 5;
},
otci_IntegerUtil = $rt_classWithoutFields(),
otci_IntegerUtil_toUnsignedLogRadixString = ($value, $radixLog2) => {
    let $radix, $mask, $sz, $chars, $pos, $target, var$9, $target_0;
    if (!$value)
        return $rt_s(22);
    $radix = 1 << $radixLog2;
    $mask = $radix - 1 | 0;
    $sz = (((32 - jl_Integer_numberOfLeadingZeros($value) | 0) + $radixLog2 | 0) - 1 | 0) / $radixLog2 | 0;
    $chars = $rt_createCharArray($sz);
    $pos = $rt_imul($sz - 1 | 0, $radixLog2);
    $target = 0;
    while ($pos >= 0) {
        var$9 = $chars.data;
        $target_0 = $target + 1 | 0;
        var$9[$target] = jl_Character_forDigit(($value >>> $pos | 0) & $mask, $radix);
        $pos = $pos - $radixLog2 | 0;
        $target = $target_0;
    }
    return jl_String__init_3($chars);
},
juf_Consumer = $rt_classWithoutFields(0);
function cpgv_GameplayScreen$render$lambda$_2_0() {
    jl_Object.call(this);
    this.$_05 = null;
}
let cpgv_GameplayScreen$render$lambda$_2_0__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_05 = var$1;
},
cpgv_GameplayScreen$render$lambda$_2_0__init_0 = var_0 => {
    let var_1 = new cpgv_GameplayScreen$render$lambda$_2_0();
    cpgv_GameplayScreen$render$lambda$_2_0__init_(var_1, var_0);
    return var_1;
},
cpgv_GameplayScreen$render$lambda$_2_0_accept0 = (var$0, var$1) => {
    cpgv_GameplayScreen$render$lambda$_2_0_accept(var$0, var$1);
},
cpgv_GameplayScreen$render$lambda$_2_0_accept = (var$0, var$1) => {
    cpgv_GameplayScreen_lambda$render$0(var$0.$_05, var$1);
},
jl_Math = $rt_classWithoutFields(),
jl_Math_sin = var$1 => {
    return Math.sin(var$1);
},
jl_Math_cos = var$1 => {
    return Math.cos(var$1);
},
jl_Math_sqrt = var$1 => {
    return Math.sqrt(var$1);
},
jl_Math_round = var$1 => {
    return var$1 + jl_Math_signum0(var$1) * 0.5 | 0;
},
jl_Math_random = () => {
    return jl_Math_randomImpl();
},
jl_Math_randomImpl = () => {
    return Math.random();
},
jl_Math_min = ($a, $b) => {
    if ($a < $b)
        $b = $a;
    return $b;
},
jl_Math_max = ($a, $b) => {
    if ($a > $b)
        $b = $a;
    return $b;
},
jl_Math_absImpl = var$1 => {
    return Math.abs(var$1);
},
jl_Math_abs = var$1 => {
    return jl_Math_absImpl(var$1);
},
jl_Math_sign0 = var$1 => {
    return Math.sign(var$1);
},
jl_Math_signum = var$1 => {
    return jl_Math_sign0(var$1);
},
jl_Math_sign = var$1 => {
    return Math.sign(var$1);
},
jl_Math_signum0 = var$1 => {
    return jl_Math_sign(var$1);
};
function cpgv_GameplayScreen$render$lambda$_2_1() {
    jl_Object.call(this);
    this.$_013 = null;
}
let cpgv_GameplayScreen$render$lambda$_2_1__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_013 = var$1;
},
cpgv_GameplayScreen$render$lambda$_2_1__init_0 = var_0 => {
    let var_1 = new cpgv_GameplayScreen$render$lambda$_2_1();
    cpgv_GameplayScreen$render$lambda$_2_1__init_(var_1, var_0);
    return var_1;
},
cpgv_GameplayScreen$render$lambda$_2_1_accept0 = (var$0, var$1) => {
    cpgv_GameplayScreen$render$lambda$_2_1_accept(var$0, var$1);
},
cpgv_GameplayScreen$render$lambda$_2_1_accept = (var$0, var$1) => {
    cpgv_GameplayScreen_lambda$render$1(var$0.$_013, var$1);
};
function cpgmc_CollisionManager$_init_$lambda$_0_7() {
    jl_Object.call(this);
    this.$_012 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_7__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_012 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_7__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_7();
    cpgmc_CollisionManager$_init_$lambda$_0_7__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_7_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_7_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_7_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$7(var$0.$_012, var$1, var$2);
};
function cpgmc_CollisionManager$_init_$lambda$_0_8() {
    jl_Object.call(this);
    this.$_04 = null;
}
let cpgmc_CollisionManager$_init_$lambda$_0_8__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_04 = var$1;
},
cpgmc_CollisionManager$_init_$lambda$_0_8__init_0 = var_0 => {
    let var_1 = new cpgmc_CollisionManager$_init_$lambda$_0_8();
    cpgmc_CollisionManager$_init_$lambda$_0_8__init_(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$_init_$lambda$_0_8_accept0 = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager$_init_$lambda$_0_8_accept(var$0, var$1, var$2);
},
cpgmc_CollisionManager$_init_$lambda$_0_8_accept = (var$0, var$1, var$2) => {
    cpgmc_CollisionManager_lambda$new$8(var$0.$_04, var$1, var$2);
},
cpgmc_CollisionDetection = $rt_classWithoutFields(),
cpgmc_CollisionDetection_detect0 = ($theVO, $theRB) => {
    let $normal, $bestEdge, $bestProjection, var$6, var$7, var$8, $e, $edgeNormalOfRB, $projection, $penVector;
    $normal = null;
    $bestEdge = null;
    $bestProjection = (-Infinity);
    var$6 = ($theRB.$getEdges()).data;
    var$7 = var$6.length;
    var$8 = 0;
    while (var$8 < var$7) {
        $e = var$6[var$8];
        $edgeNormalOfRB = cpgmr_RigidBodyEdge_getPerp($e);
        $projection = (($theVO.$getPosition()).$subNew(cpgmr_RigidBodyEdge_getStart($e))).$dotProduct($edgeNormalOfRB.$normNew());
        if ($projection > $bestProjection) {
            $bestProjection = $projection;
            $normal = $edgeNormalOfRB;
            $bestEdge = $e;
        }
        var$8 = var$8 + 1 | 0;
    }
    if ($bestProjection <= 0.0 && $bestEdge !== null) {
        $penVector = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($bestEdge), cpgmr_RigidBodyEdge_getEnd($bestEdge), $theVO.$getPosition());
        $penVector.$sub($theVO.$getPosition());
        return cpgmc_Manifold__init_($theVO.$getPosition(), $normal, $penVector);
    }
    return null;
},
cpgmc_CollisionDetection_detect1 = ($theVO, $theRC) => {
    let $radiusSq, $toOrigin, $penVector;
    $radiusSq = $theRC.$getRadius() * $theRC.$getRadius();
    $toOrigin = ($theVO.$getPosition()).$subNew($theRC.$getCenterOfMass());
    if ($toOrigin.$dotProduct($toOrigin) > $radiusSq)
        return null;
    $penVector = $toOrigin.$normNew();
    $penVector.$mul($theRC.$getRadius());
    $penVector.$add($theRC.$getCenterOfMass());
    $penVector.$sub($theVO.$getPosition());
    return cpgmc_Manifold__init_($theVO.$getPosition(), $toOrigin.$mulNew((-1.0)), $penVector);
},
cpgmc_CollisionDetection_detect3 = ($theA, $theB) => {
    let $ab, var$4, $ba, var$6, $edgeA, $edgeB, $collisionPoint2, $edgesParallel, $projAB, $projBA, $collisionPoint, $collisionNormal, $penVector, $vel, $penAB, $projStart, $projEnd, $projCenterBA, $projCenterAB, $proj;
    $ab = cpgu_VMath_findAxisOfPenetration($theA, $theB);
    var$4 = $ab.data;
    if (!var$4.length)
        return null;
    $ba = cpgu_VMath_findAxisOfPenetration($theB, $theA);
    var$6 = $ba.data;
    if (!var$6.length)
        return null;
    $edgeA = ($theA.$getEdges()).data[var$4[2].$intX()];
    $edgeB = ($theB.$getEdges()).data[var$6[2].$intX()];
    $collisionPoint2 = null;
    $edgesParallel = jl_Math_abs((cpgmr_RigidBodyEdge_getEdge($edgeA)).$crossProduct(cpgmr_RigidBodyEdge_getEdge($edgeB))) >= 1.0E-5 ? 0 : 1;
    if (!$edgesParallel) {
        $projAB = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeB), cpgmr_RigidBodyEdge_getEnd($edgeB), var$6[0]);
        $projBA = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), var$4[0]);
        if ($projAB === null) {
            $collisionPoint = var$4[0];
            $collisionNormal = var$4[1];
            $penVector = $collisionPoint.$subNew($projBA);
        } else if ($projBA === null) {
            $collisionPoint = var$6[0];
            $collisionNormal = var$6[1];
            $penVector = $projAB.$subNew($collisionPoint);
        } else {
            $vel = ($theA.$getLinearVelocity()).$mulNew((-1.0));
            $penAB = $projAB.$subNew(var$6[0]);
            $penVector = var$4[0].$subNew($projBA);
            if ($vel.$dotProduct($penAB) > $vel.$dotProduct($penVector))
                $penVector = $penAB;
            $collisionPoint = $penVector !== $penAB ? var$4[0] : var$6[0];
            $collisionNormal = $penVector !== $penAB ? var$4[1] : var$6[1];
        }
    } else {
        $projStart = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), cpgmr_RigidBodyEdge_getStart($edgeB));
        $projEnd = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeB));
        if (!(($projStart !== null ? 0 : 1) ^ ($projEnd !== null ? 0 : 1))) {
            $collisionPoint = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), $theA.$getCenterOfMass());
            $projCenterBA = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), $theB.$getCenterOfMass());
            if ($projCenterBA !== null)
                $collisionPoint2 = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeB), cpgmr_RigidBodyEdge_getEnd($edgeB), $theB.$getCenterOfMass());
        } else {
            $projCenterAB = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeB), cpgmr_RigidBodyEdge_getEnd($edgeB), $theA.$getCenterOfMass());
            $projCenterBA = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), $theB.$getCenterOfMass());
            $collisionPoint = $projCenterAB !== null ? cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeA), cpgmr_RigidBodyEdge_getEnd($edgeA), $theA.$getCenterOfMass()) : $projStart !== null ? cpgu_VMath_findMidpoint($projStart, cpgmr_RigidBodyEdge_getStart($edgeA)) : cpgu_VMath_findMidpoint($projEnd, cpgmr_RigidBodyEdge_getEnd($edgeA));
            if ($projCenterBA !== null)
                $collisionPoint2 = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeB), cpgmr_RigidBodyEdge_getEnd($edgeB), $theB.$getCenterOfMass());
        }
        $collisionNormal = var$6[1];
        $proj = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($edgeB), cpgmr_RigidBodyEdge_getEnd($edgeB), $collisionPoint);
        $penVector = $proj.$subNew($collisionPoint);
    }
    if ($collisionPoint2 !== null)
        return cpgmc_Manifold__init_2($collisionPoint, $collisionPoint2, $collisionNormal, $penVector);
    return cpgmc_Manifold__init_($collisionPoint, $collisionNormal, $penVector);
},
cpgmc_CollisionDetection_detect = ($theRC, $theRB) => {
    let $bestEdge, $bestProjection, var$5, var$6, var$7, $e, $edgeNormal, $projection, $radiusSq, $projOnEdge, $flip, $toProj, $collisionPoint, $penVector, $collisionNormal, var$18;
    $bestEdge = null;
    $bestProjection = (-Infinity);
    var$5 = ($theRB.$getEdges()).data;
    var$6 = var$5.length;
    var$7 = 0;
    while (var$7 < var$6) {
        $e = var$5[var$7];
        $edgeNormal = (cpgmr_RigidBodyEdge_getPerp($e)).$normNew();
        $projection = (($theRC.$getCenterOfMass()).$subNew(cpgmr_RigidBodyEdge_getStart($e))).$dotProduct($edgeNormal);
        if ($projection > $bestProjection) {
            $bestProjection = $projection;
            $bestEdge = $e;
        }
        var$7 = var$7 + 1 | 0;
    }
    $radiusSq = $theRC.$getRadius() * $theRC.$getRadius();
    $projOnEdge = cpgu_VMath_project(cpgmr_RigidBodyEdge_getStart($bestEdge), cpgmr_RigidBodyEdge_getEnd($bestEdge), $theRC.$getCenterOfMass());
    $flip = 1.0;
    if ($projOnEdge === null)
        $projOnEdge = (($theRC.$getCenterOfMass()).$subNew(cpgmr_RigidBodyEdge_getStart($bestEdge))).$dotProduct(cpgmr_RigidBodyEdge_getEdge($bestEdge)) >= 0.0 ? cpgmr_RigidBodyEdge_getEnd($bestEdge) : cpgmr_RigidBodyEdge_getStart($bestEdge);
    else if ((($theRC.$getCenterOfMass()).$subNew(cpgmr_RigidBodyEdge_getStart($bestEdge))).$crossProduct(cpgmr_RigidBodyEdge_getEdge($bestEdge)) < 0.0)
        $flip = (-1.0);
    $toProj = $projOnEdge.$subNew($theRC.$getCenterOfMass());
    if ($flip > 0.0 && $toProj.$dotProduct($toProj) > $radiusSq)
        return null;
    $collisionPoint = cpgu_Vector2__init_1($projOnEdge);
    $penVector = $toProj.$normNew();
    $penVector.$mul($theRC.$getRadius() * $flip);
    $collisionNormal = cpgu_Vector2__init_( -$penVector.$getX(),  -$penVector.$getY());
    var$18 = $toProj.$subNew($penVector);
    return cpgmc_Manifold__init_($collisionPoint, $collisionNormal, var$18);
},
cpgmc_CollisionDetection_detect2 = ($theA, $theB) => {
    let $ab, $radiusSum, $collisionNormal, $collisionPoint, $penVector;
    $ab = ($theB.$getCenterOfMass()).$subNew($theA.$getCenterOfMass());
    $radiusSum = $theA.$getRadius() + $theB.$getRadius();
    if ($ab.$dotProduct($ab) > $radiusSum * $radiusSum)
        return null;
    $collisionNormal = cpgu_Vector2__init_( -$ab.$getX(),  -$ab.$getY());
    $ab.$norm();
    $collisionPoint = $ab.$mulNew($theA.$getRadius());
    $collisionPoint.$add($theA.$getCenterOfMass());
    $penVector = $ab.$mulNew( -$theB.$getRadius());
    $penVector.$add($theB.$getCenterOfMass());
    $penVector.$sub($collisionPoint);
    return cpgmc_Manifold__init_($collisionPoint, $collisionNormal, $penVector);
},
otji_JS = $rt_classWithoutFields(),
otji_JS_function = (var$1, var$2) => {
    let name = 'jso$functor$' + var$2;
    let result = var$1[name];
    if (typeof result !== 'function') {
        let fn = function() {
            return var$1[var$2].apply(var$1, arguments);
        };
        result = () => fn;
        var$1[name] = result;
    }
    return result();
};
function cpgc_GameRoot$start$lambda$_4_0() {
    jl_Object.call(this);
    this.$_08 = null;
}
let cpgc_GameRoot$start$lambda$_4_0__init_ = (var$0, var$1) => {
    jl_Object__init_(var$0);
    var$0.$_08 = var$1;
},
cpgc_GameRoot$start$lambda$_4_0__init_0 = var_0 => {
    let var_1 = new cpgc_GameRoot$start$lambda$_4_0();
    cpgc_GameRoot$start$lambda$_4_0__init_(var_1, var_0);
    return var_1;
},
cpgc_GameRoot$start$lambda$_4_0_onTimer = var$0 => {
    cpgc_GameRoot_run(var$0.$_08);
},
cpgc_GameRoot$start$lambda$_4_0_onTimer$exported$0 = var$1 => {
    var$1.$onTimer();
},
ju_Objects = $rt_classWithoutFields(),
ju_Objects_checkFromIndexSize = ($fromIndex, $size, $length) => {
    if ($fromIndex >= 0 && $size >= 0 && $size <= ($length - $fromIndex | 0))
        return $fromIndex;
    $rt_throw(jl_IndexOutOfBoundsException__init_0());
};
function ju_MapEntry() {
    let a = this; jl_Object.call(a);
    a.$key = null;
    a.$value = null;
}
let ju_MapEntry__init_ = ($this, $theKey, $theValue) => {
    jl_Object__init_($this);
    $this.$key = $theKey;
    $this.$value = $theValue;
},
ju_MapEntry__init_0 = (var_0, var_1) => {
    let var_2 = new ju_MapEntry();
    ju_MapEntry__init_(var_2, var_0, var_1);
    return var_2;
};
function ju_HashMap$HashEntry() {
    let a = this; ju_MapEntry.call(a);
    a.$origKeyHash = 0;
    a.$next0 = null;
}
let ju_HashMap$HashEntry__init_ = ($this, $theKey, $hash) => {
    ju_MapEntry__init_($this, $theKey, null);
    $this.$origKeyHash = $hash;
},
ju_HashMap$HashEntry__init_0 = (var_0, var_1) => {
    let var_2 = new ju_HashMap$HashEntry();
    ju_HashMap$HashEntry__init_(var_2, var_0, var_1);
    return var_2;
},
jlr_Type = $rt_classWithoutFields(0),
ju_AbstractSet = $rt_classWithoutFields(ju_AbstractCollection),
ju_AbstractSet__init_ = $this => {
    ju_AbstractCollection__init_($this);
};
function cpgc_GameRoot() {
    let a = this; jl_Object.call(a);
    a.$myGameCanvas = null;
    a.$loopId = 0;
    a.$lastTime = 0.0;
    a.$delta = 0.0;
}
let cpgc_GameRoot__init_ = $this => {
    let $vector, $myGameplayScreen;
    jl_Object__init_($this);
    $vector = cpgu_Vector2__init_(16.0, 9.0);
    $this.$myGameCanvas = cpgv_DrawCanvas__init_0($vector, 80);
    $vector.$set0($this.$myGameCanvas.$getWidth(), $this.$myGameCanvas.$getHeight());
    $vector.$mul(0.5);
    cpgv_GameScreen_setOrigin($vector);
    cpgv_GameScreen_setCanvas($this.$myGameCanvas);
    $myGameplayScreen = cpgv_GameplayScreen__init_0($rt_s(23));
    cpgv_GameScreen_setCurrentScreen($myGameplayScreen);
},
cpgc_GameRoot__init_0 = () => {
    let var_0 = new cpgc_GameRoot();
    cpgc_GameRoot__init_(var_0);
    return var_0;
},
cpgc_GameRoot_run = $this => {
    let $now;
    $now = Long_toNumber(jl_System_currentTimeMillis());
    $this.$delta = $this.$delta + ($now - $this.$lastTime) / 16.666666666666668;
    while ($this.$delta >= 1.0) {
        cpgc_GameRoot_tick($this);
        cpgc_GameRoot_render($this);
        $this.$delta = $this.$delta - 1.0;
    }
    $this.$lastTime = $now;
},
cpgc_GameRoot_tick = $this => {
    if (cpgv_GameScreen_getCurrentScreen() !== null)
        (cpgv_GameScreen_getCurrentScreen()).$tick();
},
cpgc_GameRoot_render = $this => {
    $this.$myGameCanvas.$clearRect(0, 0, $this.$myGameCanvas.$getWidth(), $this.$myGameCanvas.$getHeight());
    if (cpgv_GameScreen_getCurrentScreen() !== null)
        (cpgv_GameScreen_getCurrentScreen()).$render();
},
cpgc_GameRoot_start = $this => {
    let var$1;
    $this.$lastTime = Long_toNumber(jl_System_currentTimeMillis());
    var$1 = cpgc_GameRoot$start$lambda$_4_0__init_0($this);
    $this.$loopId = setInterval(otji_JS_function(otji_JSWrapper_unwrap(var$1), "onTimer"), 0);
},
cpgc_GameRoot_stop = $this => {
    let var$1;
    var$1 = $this.$loopId;
    clearInterval(var$1);
},
ju_AbstractMap = $rt_classWithoutFields(),
ju_AbstractMap__init_ = $this => {
    jl_Object__init_($this);
};
function ju_HashMap() {
    let a = this; ju_AbstractMap.call(a);
    a.$elementCount = 0;
    a.$elementData = null;
    a.$modCount0 = 0;
    a.$loadFactor = 0.0;
    a.$threshold = 0;
}
let ju_HashMap_newElementArray = ($this, $s) => {
    return $rt_createArray(ju_HashMap$HashEntry, $s);
},
ju_HashMap__init_1 = $this => {
    ju_HashMap__init_($this, 16);
},
ju_HashMap__init_2 = () => {
    let var_0 = new ju_HashMap();
    ju_HashMap__init_1(var_0);
    return var_0;
},
ju_HashMap__init_ = ($this, $capacity) => {
    ju_HashMap__init_0($this, $capacity, 0.75);
},
ju_HashMap__init_3 = var_0 => {
    let var_1 = new ju_HashMap();
    ju_HashMap__init_(var_1, var_0);
    return var_1;
},
ju_HashMap_calculateCapacity = $x => {
    let var$2, var$3;
    if ($x >= 1073741824)
        return 1073741824;
    if (!$x)
        return 16;
    var$2 = $x - 1 | 0;
    var$3 = var$2 | var$2 >> 1;
    var$3 = var$3 | var$3 >> 2;
    var$3 = var$3 | var$3 >> 4;
    var$3 = var$3 | var$3 >> 8;
    var$3 = var$3 | var$3 >> 16;
    return var$3 + 1 | 0;
},
ju_HashMap__init_0 = ($this, $capacity, $loadFactor) => {
    let var$3;
    ju_AbstractMap__init_($this);
    if ($capacity >= 0 && $loadFactor > 0.0) {
        var$3 = ju_HashMap_calculateCapacity($capacity);
        $this.$elementCount = 0;
        $this.$elementData = $this.$newElementArray(var$3);
        $this.$loadFactor = $loadFactor;
        ju_HashMap_computeThreshold($this);
        return;
    }
    $rt_throw(jl_IllegalArgumentException__init_0());
},
ju_HashMap__init_4 = (var_0, var_1) => {
    let var_2 = new ju_HashMap();
    ju_HashMap__init_0(var_2, var_0, var_1);
    return var_2;
},
ju_HashMap_computeThreshold = $this => {
    $this.$threshold = $this.$elementData.data.length * $this.$loadFactor | 0;
},
ju_HashMap_containsKey = ($this, $key) => {
    let $m;
    $m = ju_HashMap_entryByKey($this, $key);
    return $m === null ? 0 : 1;
},
ju_HashMap_get = ($this, $key) => {
    let $m;
    $m = ju_HashMap_entryByKey($this, $key);
    if ($m === null)
        return null;
    return $m.$value;
},
ju_HashMap_entryByKey = ($this, $key) => {
    let $m, $hash, $index;
    if ($key === null)
        $m = ju_HashMap_findNullKeyEntry($this);
    else {
        $hash = $key.$hashCode0();
        $index = $hash & ($this.$elementData.data.length - 1 | 0);
        $m = ju_HashMap_findNonNullKeyEntry($this, $key, $index, $hash);
    }
    return $m;
},
ju_HashMap_findNonNullKeyEntry = ($this, $key, $index, $keyHash) => {
    let $m;
    $m = $this.$elementData.data[$index];
    while ($m !== null && !($m.$origKeyHash == $keyHash && ju_HashMap_areEqualKeys($key, $m.$key))) {
        $m = $m.$next0;
    }
    return $m;
},
ju_HashMap_findNullKeyEntry = $this => {
    let $m;
    $m = $this.$elementData.data[0];
    while ($m !== null && $m.$key !== null) {
        $m = $m.$next0;
    }
    return $m;
},
ju_HashMap_put = ($this, $key, $value) => {
    return ju_HashMap_putImpl($this, $key, $value);
},
ju_HashMap_putImpl = ($this, $key, $value) => {
    let $entry, var$4, $hash, $index, $result;
    if ($key === null) {
        $entry = ju_HashMap_findNullKeyEntry($this);
        if ($entry === null) {
            $this.$modCount0 = $this.$modCount0 + 1 | 0;
            $entry = ju_HashMap_createHashedEntry($this, null, 0, 0);
            var$4 = $this.$elementCount + 1 | 0;
            $this.$elementCount = var$4;
            if (var$4 > $this.$threshold)
                $this.$rehash();
        }
    } else {
        $hash = $key.$hashCode0();
        $index = $hash & ($this.$elementData.data.length - 1 | 0);
        $entry = ju_HashMap_findNonNullKeyEntry($this, $key, $index, $hash);
        if ($entry === null) {
            $this.$modCount0 = $this.$modCount0 + 1 | 0;
            $entry = ju_HashMap_createHashedEntry($this, $key, $index, $hash);
            var$4 = $this.$elementCount + 1 | 0;
            $this.$elementCount = var$4;
            if (var$4 > $this.$threshold)
                $this.$rehash();
        }
    }
    $result = $entry.$value;
    $entry.$value = $value;
    return $result;
},
ju_HashMap_createHashedEntry = ($this, $key, $index, $hash) => {
    let $entry;
    $entry = ju_HashMap$HashEntry__init_0($key, $hash);
    $entry.$next0 = $this.$elementData.data[$index];
    $this.$elementData.data[$index] = $entry;
    return $entry;
},
ju_HashMap_rehash = ($this, $capacity) => {
    let $length, $newData, $i, $entry, var$6, $index, $next;
    $length = ju_HashMap_calculateCapacity(!$capacity ? 1 : $capacity << 1);
    $newData = $this.$newElementArray($length);
    $i = 0;
    while ($i < $this.$elementData.data.length) {
        $entry = $this.$elementData.data[$i];
        $this.$elementData.data[$i] = null;
        while ($entry !== null) {
            var$6 = $newData.data;
            $index = $entry.$origKeyHash & ($length - 1 | 0);
            $next = $entry.$next0;
            $entry.$next0 = var$6[$index];
            var$6[$index] = $entry;
            $entry = $next;
        }
        $i = $i + 1 | 0;
    }
    $this.$elementData = $newData;
    ju_HashMap_computeThreshold($this);
},
ju_HashMap_rehash0 = $this => {
    $this.$rehash0($this.$elementData.data.length);
},
ju_HashMap_remove = ($this, $key) => {
    let $entry;
    $entry = ju_HashMap_removeByKey($this, $key);
    if ($entry === null)
        return null;
    return $entry.$value;
},
ju_HashMap_removeByKey = ($this, $key) => {
    let $index, $last, $entry, $entry_0, $hash;
    a: {
        $index = 0;
        $last = null;
        if ($key === null) {
            $entry = $this.$elementData.data[0];
            while ($entry !== null) {
                if ($entry.$key === null)
                    break a;
                $entry_0 = $entry.$next0;
                $last = $entry;
                $entry = $entry_0;
            }
        } else {
            $hash = $key.$hashCode0();
            $index = $hash & ($this.$elementData.data.length - 1 | 0);
            $entry = $this.$elementData.data[$index];
            while ($entry !== null && !($entry.$origKeyHash == $hash && ju_HashMap_areEqualKeys($key, $entry.$key))) {
                $entry_0 = $entry.$next0;
                $last = $entry;
                $entry = $entry_0;
            }
        }
    }
    if ($entry === null)
        return null;
    if ($last !== null)
        $last.$next0 = $entry.$next0;
    else
        $this.$elementData.data[$index] = $entry.$next0;
    $this.$modCount0 = $this.$modCount0 + 1 | 0;
    $this.$elementCount = $this.$elementCount - 1 | 0;
    return $entry;
},
ju_HashMap_areEqualKeys = ($key1, $key2) => {
    return $key1 !== $key2 && !$key1.$equals($key2) ? 0 : 1;
};
function otji_JSWrapper() {
    jl_Object.call(this);
    this.$js = null;
}
let otji_JSWrapper_unwrap = $o => {
    if ($o === null)
        return null;
    return !($o instanceof otji_JSWrapper) ? $o : $o.$js;
};
function ju_HashSet() {
    ju_AbstractSet.call(this);
    this.$backingMap = null;
}
let ju_HashSet__init_0 = $this => {
    ju_HashSet__init_($this, ju_HashMap__init_2());
},
ju_HashSet__init_1 = () => {
    let var_0 = new ju_HashSet();
    ju_HashSet__init_0(var_0);
    return var_0;
},
ju_HashSet__init_ = ($this, $backingMap) => {
    ju_AbstractSet__init_($this);
    $this.$backingMap = $backingMap;
},
ju_HashSet__init_2 = var_0 => {
    let var_1 = new ju_HashSet();
    ju_HashSet__init_(var_1, var_0);
    return var_1;
},
ju_HashSet_add = ($this, $object) => {
    return $this.$backingMap.$put($object, $this) !== null ? 0 : 1;
},
ju_HashSet_contains = ($this, $object) => {
    return $this.$backingMap.$containsKey($object);
},
ju_HashSet_remove = ($this, $object) => {
    return $this.$backingMap.$remove($object) === null ? 0 : 1;
},
otp_Platform = $rt_classWithoutFields(),
otp_Platform_clone = var$1 => {
    let copy = new var$1.constructor();
    for (let field in var$1) {
        if (var$1.hasOwnProperty(field)) {
            copy[field] = var$1[field];
        }
    }
    return copy;
},
otp_Platform_getArrayItem = var$1 => {
    return var$1.$meta.item;
},
otp_Platform_getName = $cls => {
    return $rt_str($cls.$meta.name);
},
cpgc_GameplayController$update$lambda$_1_0 = $rt_classWithoutFields(),
cpgc_GameplayController$update$lambda$_1_0__init_ = var$0 => {
    jl_Object__init_(var$0);
},
cpgc_GameplayController$update$lambda$_1_0__init_0 = () => {
    let var_0 = new cpgc_GameplayController$update$lambda$_1_0();
    cpgc_GameplayController$update$lambda$_1_0__init_(var_0);
    return var_0;
},
cpgc_GameplayController$update$lambda$_1_0_accept0 = (var$0, var$1) => {
    cpgc_GameplayController$update$lambda$_1_0_accept(var$0, var$1);
},
cpgc_GameplayController$update$lambda$_1_0_accept = (var$0, var$1) => {
    var$1.$update();
},
jl_IllegalArgumentException = $rt_classWithoutFields(jl_RuntimeException),
jl_IllegalArgumentException__init_1 = $this => {
    jl_RuntimeException__init_($this);
},
jl_IllegalArgumentException__init_0 = () => {
    let var_0 = new jl_IllegalArgumentException();
    jl_IllegalArgumentException__init_1(var_0);
    return var_0;
},
jl_IllegalArgumentException__init_2 = ($this, $message) => {
    jl_RuntimeException__init_0($this, $message);
},
jl_IllegalArgumentException__init_ = var_0 => {
    let var_1 = new jl_IllegalArgumentException();
    jl_IllegalArgumentException__init_2(var_1, var_0);
    return var_1;
},
cpgmc_CollisionManager$handleCollisions$lambda$_3_0 = $rt_classWithoutFields(),
cpgmc_CollisionManager$handleCollisions$lambda$_3_0__init_ = var$0 => {
    jl_Object__init_(var$0);
},
cpgmc_CollisionManager$handleCollisions$lambda$_3_0__init_0 = () => {
    let var_0 = new cpgmc_CollisionManager$handleCollisions$lambda$_3_0();
    cpgmc_CollisionManager$handleCollisions$lambda$_3_0__init_(var_0);
    return var_0;
},
cpgmc_CollisionManager$handleCollisions$lambda$_3_0_accept0 = (var$0, var$1) => {
    cpgmc_CollisionManager$handleCollisions$lambda$_3_0_accept(var$0, var$1);
},
cpgmc_CollisionManager$handleCollisions$lambda$_3_0_accept = (var$0, var$1) => {
    var$1.$handleResponse();
};
function cpgmc_Manifold() {
    let a = this; jl_Object.call(a);
    a.$myCollisionPoint = null;
    a.$myCollisionPoint2 = null;
    a.$myCollisionNormal = null;
    a.$myPenetrationVector = null;
}
let cpgmc_Manifold__init_1 = ($this, $theCollisionPoint, $theCollisionNormal, $thePenetrationVector) => {
    cpgmc_Manifold__init_0($this, $theCollisionPoint, null, $theCollisionNormal, $thePenetrationVector);
},
cpgmc_Manifold__init_ = (var_0, var_1, var_2) => {
    let var_3 = new cpgmc_Manifold();
    cpgmc_Manifold__init_1(var_3, var_0, var_1, var_2);
    return var_3;
},
cpgmc_Manifold__init_0 = ($this, $theCollisionPoint, $theCollisionPoint2, $theCollisionNormal, $thePenetrationVector) => {
    jl_Object__init_($this);
    $this.$myCollisionPoint = $theCollisionPoint;
    $this.$myCollisionNormal = $theCollisionNormal;
    $this.$myPenetrationVector = $thePenetrationVector;
    $this.$myCollisionPoint2 = $theCollisionPoint2;
},
cpgmc_Manifold__init_2 = (var_0, var_1, var_2, var_3) => {
    let var_4 = new cpgmc_Manifold();
    cpgmc_Manifold__init_0(var_4, var_0, var_1, var_2, var_3);
    return var_4;
},
cpgmc_Manifold_getNormal = $this => {
    return $this.$myCollisionNormal;
},
cpgmc_Manifold_getPoint = $this => {
    return $this.$myCollisionPoint;
},
cpgmc_Manifold_getPoint2 = $this => {
    return $this.$myCollisionPoint2;
},
cpgmc_Manifold_getPenetration = $this => {
    return $this.$myPenetrationVector;
},
cpgmp_VerletObject = $rt_classWithoutFields(cpgm_GameObject);
function cpgci_Mouse() {
    let a = this; jl_Object.call(a);
    a.$myOrigin0 = null;
    a.$myOffset = null;
    a.$myPosition0 = null;
    a.$myButtonUps = null;
    a.$myButtonDowns = null;
    a.$myButtonHelds = null;
}
let cpgci_Mouse__init_ = ($this, $theOffset, $theOrigin) => {
    jl_Object__init_($this);
    $this.$myPosition0 = cpgu_Vector2__init_0();
    $this.$myOffset = cpgu_Vector2__init_1($theOffset);
    $this.$myOrigin0 = cpgu_Vector2__init_1($theOrigin);
    $this.$myButtonDowns = $rt_createBooleanArray(8);
    $this.$myButtonUps = $rt_createBooleanArray(8);
    $this.$myButtonHelds = ju_HashSet__init_1();
},
cpgci_Mouse__init_0 = (var_0, var_1) => {
    let var_2 = new cpgci_Mouse();
    cpgci_Mouse__init_(var_2, var_0, var_1);
    return var_2;
},
cpgci_Mouse_getPos = $this => {
    return $this.$myPosition0;
},
cpgci_Mouse_enumToInt = ($this, $theClick) => {
    let var$2;
    a: {
        cpgci_Mouse$1_$callClinit();
        switch (cpgci_Mouse$1_$SwitchMap$com$physicsim$game$controller$input$ClickType.data[jl_Enum_ordinal($theClick)]) {
            case 1:
                break;
            case 2:
                var$2 = 2;
                break a;
            case 3:
                var$2 = 1;
                break a;
            case 4:
                var$2 = 3;
                break a;
            case 5:
                var$2 = 4;
                break a;
            default:
                $rt_throw(jl_IncompatibleClassChangeError__init_0());
        }
        var$2 = 0;
    }
    return var$2;
},
cpgci_Mouse_isButtonDown = ($this, $theClick) => {
    let $button;
    $button = cpgci_Mouse_enumToInt($this, $theClick);
    if ($this.$myButtonDowns.data[$button])
        return 0;
    $this.$myButtonDowns.data[$button] = $this.$myButtonHelds.$contains(jl_Integer_valueOf($button));
    return $this.$myButtonDowns.data[$button];
},
cpgci_Mouse_onMouseDown = ($this, $theEvent) => {
    let $button;
    $button = $theEvent.button;
    $this.$myButtonHelds.$add0(jl_Integer_valueOf($button));
    $this.$myButtonUps.data[$button] = 0;
},
cpgci_Mouse_onMouseUp = ($this, $theEvent) => {
    let $button;
    $button = $theEvent.button;
    $this.$myButtonHelds.$remove0(jl_Integer_valueOf($button));
    $this.$myButtonDowns.data[$button] = 0;
    $this.$myButtonUps.data[$button] = 1;
},
cpgci_Mouse_onMouseMove = ($this, $theEvent) => {
    let $x, $y;
    $x = $theEvent.clientX - $this.$myOffset.$getX() - $this.$myOrigin0.$getX();
    $y = $theEvent.clientY - $this.$myOffset.$getY() - $this.$myOrigin0.$getY();
    $this.$myPosition0.$set0($x, $y);
},
cpgci_Mouse_addListenersCanvas = ($this, $theCanvas) => {
    let var$2, var$3, var$4, var$5, var$6;
    var$2 = $theCanvas.$getCanvas();
    var$3 = cpgci_Mouse$addListenersCanvas$lambda$_12_0__init_0($this);
    var$2.addEventListener("mousedown", otji_JS_function(otji_JSWrapper_unwrap(var$3), "handleEvent"));
    var$4 = $theCanvas.$getCanvas();
    var$5 = cpgci_Mouse$addListenersCanvas$lambda$_12_1__init_0($this);
    var$4.addEventListener("mouseup", otji_JS_function(otji_JSWrapper_unwrap(var$5), "handleEvent"));
    var$4 = $theCanvas.$getCanvas();
    var$6 = cpgci_Mouse$addListenersCanvas$lambda$_12_2__init_0($this);
    var$4.addEventListener("mousemove", otji_JS_function(otji_JSWrapper_unwrap(var$6), "handleEvent"));
};
function jl_Class() {
    let a = this; jl_Object.call(a);
    a.$name = null;
    a.$platformClass = null;
}
let jl_Class__init_ = ($this, $platformClass) => {
    let var$2;
    jl_Object__init_($this);
    $this.$platformClass = $platformClass;
    var$2 = $this;
    $platformClass.classObject = var$2;
},
jl_Class__init_0 = var_0 => {
    let var_1 = new jl_Class();
    jl_Class__init_(var_1, var_0);
    return var_1;
},
jl_Class_getClass = $cls => {
    let $result;
    if ($cls === null)
        return null;
    $result = $cls.classObject;
    if ($result === null)
        $result = jl_Class__init_0($cls);
    return $result;
},
jl_Class_getPlatformClass = $this => {
    return $this.$platformClass;
},
jl_Class_getName = $this => {
    if ($this.$name === null)
        $this.$name = otp_Platform_getName($this.$platformClass);
    return $this.$name;
},
jl_Class_getComponentType = $this => {
    return jl_Class_getClass(otp_Platform_getArrayItem($this.$platformClass));
},
ju_Comparator = $rt_classWithoutFields(0),
jl_String$_clinit_$lambda$_115_0 = $rt_classWithoutFields(),
jl_String$_clinit_$lambda$_115_0__init_ = var$0 => {
    jl_Object__init_(var$0);
},
jl_String$_clinit_$lambda$_115_0__init_0 = () => {
    let var_0 = new jl_String$_clinit_$lambda$_115_0();
    jl_String$_clinit_$lambda$_115_0__init_(var_0);
    return var_0;
};
$rt_packages([-1, "java", 0, "lang"
]);
$rt_metadata([jl_Object, "Object", 1, 0, [], 0, 3, 0, 0, ["$getClass0", $rt_wrapFunction0(jl_Object_getClass), "$toString", $rt_wrapFunction0(jl_Object_toString), "$identity", $rt_wrapFunction0(jl_Object_identity), "$clone0", $rt_wrapFunction0(jl_Object_clone)],
cpgv_GameScreen, 0, jl_Object, [], 1, 3, 0, 0, ["$_init_1", $rt_wrapFunction1(cpgv_GameScreen__init_)],
jl_Throwable, 0, jl_Object, [], 0, 3, 0, 0, ["$fillInStackTrace", $rt_wrapFunction0(jl_Throwable_fillInStackTrace), "$getMessage", $rt_wrapFunction0(jl_Throwable_getMessage), "$getCause", $rt_wrapFunction0(jl_Throwable_getCause)],
jl_Exception, 0, jl_Throwable, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_Exception__init_), "$_init_1", $rt_wrapFunction1(jl_Exception__init_0)],
jl_RuntimeException, 0, jl_Exception, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_RuntimeException__init_), "$_init_1", $rt_wrapFunction1(jl_RuntimeException__init_0)],
jl_IndexOutOfBoundsException, 0, jl_RuntimeException, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_IndexOutOfBoundsException__init_)],
ju_Arrays, 0, jl_Object, [], 0, 3, 0, 0, 0,
cpg_Launcher, 0, jl_Object, [], 4, 3, 0, cpg_Launcher_$callClinit, 0,
cpgm_GameObject, 0, jl_Object, [], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(cpgm_GameObject__init_)],
cpgmr_Rigid2D, 0, cpgm_GameObject, [], 1, 3, 0, 0, ["$_init_4", $rt_wrapFunction2(cpgmr_Rigid2D__init_), "$setDynamics", $rt_wrapFunction1(cpgmr_Rigid2D_setDynamics), "$applyGravity", $rt_wrapFunction0(cpgmr_Rigid2D_applyGravity), "$setLinearVelocity", $rt_wrapFunction1(cpgmr_Rigid2D_setLinearVelocity), "$setAngularVelocity", $rt_wrapFunction1(cpgmr_Rigid2D_setAngularVelocity), "$applyImpulse", $rt_wrapFunction3(cpgmr_Rigid2D_applyImpulse), "$update", $rt_wrapFunction0(cpgmr_Rigid2D_update), "$preMove", $rt_wrapFunction0(cpgmr_Rigid2D_preMove),
"$postMove", $rt_wrapFunction0(cpgmr_Rigid2D_postMove), "$getLinearVelocity", $rt_wrapFunction0(cpgmr_Rigid2D_getLinearVelocity), "$getAngularVelocity", $rt_wrapFunction0(cpgmr_Rigid2D_getAngularVelocity), "$getLinearAngularVelocity", $rt_wrapFunction1(cpgmr_Rigid2D_getLinearAngularVelocity), "$getCenterOfMass", $rt_wrapFunction0(cpgmr_Rigid2D_getCenterOfMass), "$getSplitMass", $rt_wrapFunction0(cpgmr_Rigid2D_getSplitMass), "$getMoi", $rt_wrapFunction0(cpgmr_Rigid2D_getMoi), "$hasDynamics", $rt_wrapFunction0(cpgmr_Rigid2D_hasDynamics),
"$incCollision", $rt_wrapFunction0(cpgmr_Rigid2D_incCollision)],
cpgmr_RigidBody, 0, cpgmr_Rigid2D, [], 1, 3, 0, 0, ["$_init_7", $rt_wrapFunction2(cpgmr_RigidBody__init_0), "$_init_3", $rt_wrapFunction3(cpgmr_RigidBody__init_), "$translate", $rt_wrapFunction1(cpgmr_RigidBody_translate), "$move", $rt_wrapFunction0(cpgmr_RigidBody_move), "$getEdges", $rt_wrapFunction0(cpgmr_RigidBody_getEdges), "$getVertices", $rt_wrapFunction0(cpgmr_RigidBody_getVertices), "$getName", $rt_wrapFunction0(cpgmr_RigidBody_getName)],
cpgmr_Box, 0, cpgmr_RigidBody, [], 0, 3, 0, 0, ["$_init_10", $rt_wrapFunction4(cpgmr_Box__init_0), "$accept1", $rt_wrapFunction1(cpgmr_Box_accept)],
otj_JSObject, 0, jl_Object, [], 3, 3, 0, 0, 0,
otjde_EventListener, 0, jl_Object, [otj_JSObject], 3, 3, 0, 0, 0,
cpg_Launcher$main$lambda$_1_0, 0, jl_Object, [otjde_EventListener], 0, 3, 0, 0, ["$_init_2", $rt_wrapFunction1(cpg_Launcher$main$lambda$_1_0__init_), "$handleEvent", $rt_wrapFunction1(cpg_Launcher$main$lambda$_1_0_handleEvent)],
jl_System, 0, jl_Object, [], 4, 3, 0, 0, 0,
cpgmr_RigidBodyEdge, 0, jl_Object, [], 4, 3, 0, 0, ["$_init_5", $rt_wrapFunction2(cpgmr_RigidBodyEdge__init_), "$getStart", $rt_wrapFunction0(cpgmr_RigidBodyEdge_getStart), "$getEnd", $rt_wrapFunction0(cpgmr_RigidBodyEdge_getEnd), "$getEdge", $rt_wrapFunction0(cpgmr_RigidBodyEdge_getEdge), "$getPerp", $rt_wrapFunction0(cpgmr_RigidBodyEdge_getPerp)],
ji_Serializable, 0, jl_Object, [], 3, 3, 0, 0, 0,
jl_Number, 0, jl_Object, [ji_Serializable], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_Number__init_)],
jl_Comparable, 0, jl_Object, [], 3, 3, 0, 0, 0,
jl_Integer, 0, jl_Number, [jl_Comparable], 0, 3, 0, jl_Integer_$callClinit, ["$_init_8", $rt_wrapFunction1(jl_Integer__init_), "$hashCode0", $rt_wrapFunction0(jl_Integer_hashCode), "$equals", $rt_wrapFunction1(jl_Integer_equals)],
jl_CloneNotSupportedException, 0, jl_Exception, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_CloneNotSupportedException__init_)],
jl_Character, 0, jl_Object, [jl_Comparable], 0, 3, 0, jl_Character_$callClinit, 0,
ju_Map, 0, jl_Object, [], 3, 3, 0, 0, 0,
jl_CharSequence, 0, jl_Object, [], 3, 3, 0, 0, 0,
jl_Error, 0, jl_Throwable, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_Error__init_)],
jl_LinkageError, 0, jl_Error, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_LinkageError__init_)],
cpgci_Mouse$addListenersCanvas$lambda$_12_1, 0, jl_Object, [otjde_EventListener], 0, 3, 0, 0, ["$_init_31", $rt_wrapFunction1(cpgci_Mouse$addListenersCanvas$lambda$_12_1__init_), "$handleEvent", $rt_wrapFunction1(cpgci_Mouse$addListenersCanvas$lambda$_12_1_handleEvent)],
jl_Iterable, 0, jl_Object, [], 3, 3, 0, 0, 0,
ju_Collection, 0, jl_Object, [jl_Iterable], 3, 3, 0, 0, 0,
ju_Set, 0, jl_Object, [ju_Collection], 3, 3, 0, 0, 0,
cpgci_Mouse$addListenersCanvas$lambda$_12_2, 0, jl_Object, [otjde_EventListener], 0, 3, 0, 0, ["$_init_31", $rt_wrapFunction1(cpgci_Mouse$addListenersCanvas$lambda$_12_2__init_), "$handleEvent", $rt_wrapFunction1(cpgci_Mouse$addListenersCanvas$lambda$_12_2_handleEvent)],
jl_StringIndexOutOfBoundsException, 0, jl_IndexOutOfBoundsException, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_StringIndexOutOfBoundsException__init_)],
cpgci_Mouse$addListenersCanvas$lambda$_12_0, 0, jl_Object, [otjde_EventListener], 0, 3, 0, 0, ["$_init_31", $rt_wrapFunction1(cpgci_Mouse$addListenersCanvas$lambda$_12_0__init_), "$handleEvent", $rt_wrapFunction1(cpgci_Mouse$addListenersCanvas$lambda$_12_0_handleEvent)],
cpgc_GameplayController, 0, jl_Object, [], 0, 3, 0, 0, ["$_init_20", $rt_wrapFunction1(cpgc_GameplayController__init_), "$update", $rt_wrapFunction0(cpgc_GameplayController_update), "$getGameWorld", $rt_wrapFunction0(cpgc_GameplayController_getGameWorld)],
cpgmcr_CollisionResponse, 0, jl_Object, [], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(cpgmcr_CollisionResponse__init_)],
otjb_TimerHandler, 0, jl_Object, [otj_JSObject], 3, 3, 0, 0, 0,
jl_AbstractStringBuilder, 0, jl_Object, [ji_Serializable, jl_CharSequence], 0, 0, 0, 0, ["$_init_", $rt_wrapFunction0(jl_AbstractStringBuilder__init_0), "$_init_8", $rt_wrapFunction1(jl_AbstractStringBuilder__init_), "$append1", $rt_wrapFunction1(jl_AbstractStringBuilder_append), "$insert1", $rt_wrapFunction2(jl_AbstractStringBuilder_insert), "$append2", $rt_wrapFunction1(jl_AbstractStringBuilder_append0), "$insert0", $rt_wrapFunction2(jl_AbstractStringBuilder_insert1), "$insert", $rt_wrapFunction2(jl_AbstractStringBuilder_insert0),
"$ensureCapacity", $rt_wrapFunction1(jl_AbstractStringBuilder_ensureCapacity), "$toString", $rt_wrapFunction0(jl_AbstractStringBuilder_toString)],
jl_Appendable, 0, jl_Object, [], 3, 3, 0, 0, 0,
jl_StringBuilder, 0, jl_AbstractStringBuilder, [jl_Appendable], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_StringBuilder__init_0), "$append", $rt_wrapFunction1(jl_StringBuilder_append), "$append0", $rt_wrapFunction1(jl_StringBuilder_append0), "$insert2", $rt_wrapFunction2(jl_StringBuilder_insert2), "$insert3", $rt_wrapFunction2(jl_StringBuilder_insert1), "$insert4", $rt_wrapFunction2(jl_StringBuilder_insert3), "$toString", $rt_wrapFunction0(jl_StringBuilder_toString), "$ensureCapacity", $rt_wrapFunction1(jl_StringBuilder_ensureCapacity),
"$insert", $rt_wrapFunction2(jl_StringBuilder_insert0), "$insert0", $rt_wrapFunction2(jl_StringBuilder_insert), "$insert1", $rt_wrapFunction2(jl_StringBuilder_insert4)],
jl_Enum, 0, jl_Object, [jl_Comparable, ji_Serializable], 1, 3, 0, 0, ["$_init_14", $rt_wrapFunction2(jl_Enum__init_), "$ordinal", $rt_wrapFunction0(jl_Enum_ordinal)],
cpgci_ClickType, 0, jl_Enum, [], 12, 3, 0, cpgci_ClickType_$callClinit, 0,
ju_ConcurrentModificationException, 0, jl_RuntimeException, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_ConcurrentModificationException__init_)],
jlr_AnnotatedElement, 0, jl_Object, [], 3, 3, 0, 0, 0,
cpgv_GameObjectVisitor, 0, jl_Object, [], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(cpgv_GameObjectVisitor__init_)],
cpgv_GameObjectRenderer, 0, cpgv_GameObjectVisitor, [], 0, 3, 0, 0, ["$_init_0", $rt_wrapFunction1(cpgv_GameObjectRenderer__init_), "$updateGraphics", $rt_wrapFunction1(cpgv_GameObjectRenderer_updateGraphics), "$visit1", $rt_wrapFunction1(cpgv_GameObjectRenderer_visit1), "$visit0", $rt_wrapFunction1(cpgv_GameObjectRenderer_visit0), "$visit2", $rt_wrapFunction1(cpgv_GameObjectRenderer_visit), "$visit", $rt_wrapFunction1(cpgv_GameObjectRenderer_visit2)],
cpgmc_CollisionManager, 0, jl_Object, [], 4, 3, 0, cpgmc_CollisionManager_$callClinit, ["$_init_9", $rt_wrapFunction1(cpgmc_CollisionManager__init_), "$detectCollisions", $rt_wrapFunction2(cpgmc_CollisionManager_detectCollisions), "$handleCollisions", $rt_wrapFunction0(cpgmc_CollisionManager_handleCollisions)],
cpgu_Vector2, 0, jl_Object, [], 0, 3, 0, 0, ["$_init_6", $rt_wrapFunction2(cpgu_Vector2__init_2), "$_init_0", $rt_wrapFunction1(cpgu_Vector2__init_4), "$_init_", $rt_wrapFunction0(cpgu_Vector2__init_3), "$add", $rt_wrapFunction1(cpgu_Vector2_add), "$sub", $rt_wrapFunction1(cpgu_Vector2_sub), "$mul", $rt_wrapFunction1(cpgu_Vector2_mul), "$div", $rt_wrapFunction1(cpgu_Vector2_div), "$norm", $rt_wrapFunction0(cpgu_Vector2_norm), "$addNew", $rt_wrapFunction1(cpgu_Vector2_addNew), "$subNew", $rt_wrapFunction1(cpgu_Vector2_subNew),
"$mulNew", $rt_wrapFunction1(cpgu_Vector2_mulNew), "$divNew", $rt_wrapFunction1(cpgu_Vector2_divNew), "$normNew", $rt_wrapFunction0(cpgu_Vector2_normNew), "$perpNew", $rt_wrapFunction0(cpgu_Vector2_perpNew), "$dotProduct", $rt_wrapFunction1(cpgu_Vector2_dotProduct), "$crossProduct", $rt_wrapFunction1(cpgu_Vector2_crossProduct), "$getMagnitude", $rt_wrapFunction0(cpgu_Vector2_getMagnitude), "$getX", $rt_wrapFunction0(cpgu_Vector2_getX), "$getY", $rt_wrapFunction0(cpgu_Vector2_getY), "$intX", $rt_wrapFunction0(cpgu_Vector2_intX),
"$intY", $rt_wrapFunction0(cpgu_Vector2_intY), "$setX", $rt_wrapFunction1(cpgu_Vector2_setX), "$setY", $rt_wrapFunction1(cpgu_Vector2_setY), "$set0", $rt_wrapFunction2(cpgu_Vector2_set), "$set", $rt_wrapFunction1(cpgu_Vector2_set0)],
jl_ClassCastException, 0, jl_RuntimeException, [], 0, 3, 0, 0, 0,
cpgmr_RigidCircle, 0, cpgmr_Rigid2D, [], 0, 3, 0, 0, ["$_init_12", $rt_wrapFunction3(cpgmr_RigidCircle__init_), "$translate", $rt_wrapFunction1(cpgmr_RigidCircle_translate), "$move", $rt_wrapFunction0(cpgmr_RigidCircle_move), "$getRadius", $rt_wrapFunction0(cpgmr_RigidCircle_getRadius), "$getDiameter", $rt_wrapFunction0(cpgmr_RigidCircle_getDiameter), "$getOrientationVector", $rt_wrapFunction0(cpgmr_RigidCircle_getOrientationVector), "$getName", $rt_wrapFunction0(cpgmr_RigidCircle_getName), "$accept1", $rt_wrapFunction1(cpgmr_RigidCircle_accept)]]);
$rt_metadata([ju_AbstractCollection, 0, jl_Object, [ju_Collection], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_AbstractCollection__init_), "$isEmpty", $rt_wrapFunction0(ju_AbstractCollection_isEmpty)],
ju_SequencedCollection, 0, jl_Object, [ju_Collection], 3, 3, 0, 0, 0,
ju_List, 0, jl_Object, [ju_SequencedCollection], 3, 3, 0, 0, 0,
ju_AbstractList, 0, ju_AbstractCollection, [ju_List], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_AbstractList__init_), "$iterator", $rt_wrapFunction0(ju_AbstractList_iterator)],
jl_Cloneable, 0, jl_Object, [], 3, 3, 0, 0, 0,
ju_RandomAccess, 0, jl_Object, [], 3, 3, 0, 0, 0,
ju_ArrayList, 0, ju_AbstractList, [jl_Cloneable, ji_Serializable, ju_RandomAccess], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_ArrayList__init_1), "$_init_8", $rt_wrapFunction1(ju_ArrayList__init_), "$ensureCapacity", $rt_wrapFunction1(ju_ArrayList_ensureCapacity), "$get0", $rt_wrapFunction1(ju_ArrayList_get), "$size", $rt_wrapFunction0(ju_ArrayList_size), "$add0", $rt_wrapFunction1(ju_ArrayList_add), "$clear", $rt_wrapFunction0(ju_ArrayList_clear), "$forEach", $rt_wrapFunction1(ju_ArrayList_forEach)],
cpgmc_CollisionResponse1D, 0, cpgmcr_CollisionResponse, [], 0, 3, 0, 0, ["$_init_16", $rt_wrapFunction3(cpgmc_CollisionResponse1D__init_1), "$_init_17", $rt_wrapFunction3(cpgmc_CollisionResponse1D__init_0), "$handleResponse", $rt_wrapFunction0(cpgmc_CollisionResponse1D_handleResponse)],
juf_BiConsumer, 0, jl_Object, [], 3, 3, 0, 0, 0,
cpgv_DrawCanvas, 0, jl_Object, [], 0, 3, 0, 0, ["$_init_27", $rt_wrapFunction2(cpgv_DrawCanvas__init_), "$setColor", $rt_wrapFunction1(cpgv_DrawCanvas_setColor), "$clearRect", $rt_wrapFunction4(cpgv_DrawCanvas_clearRect), "$drawLine", $rt_wrapFunction4(cpgv_DrawCanvas_drawLine), "$drawOval", $rt_wrapFunction4(cpgv_DrawCanvas_drawOval), "$fillOval", $rt_wrapFunction4(cpgv_DrawCanvas_fillOval), "$getCanvas", $rt_wrapFunction0(cpgv_DrawCanvas_getCanvas), "$getWidth", $rt_wrapFunction0(cpgv_DrawCanvas_getWidth),
"$getHeight", $rt_wrapFunction0(cpgv_DrawCanvas_getHeight)],
jl_String, 0, jl_Object, [ji_Serializable, jl_Comparable, jl_CharSequence], 0, 3, 0, jl_String_$callClinit, ["$_init_", $rt_wrapFunction0(jl_String__init_), "$_init_23", $rt_wrapFunction1(jl_String__init_0), "$_init_33", $rt_wrapFunction1(jl_String__init_1), "$_init_13", $rt_wrapFunction3(jl_String__init_2), "$charAt", $rt_wrapFunction1(jl_String_charAt), "$length", $rt_wrapFunction0(jl_String_length), "$isEmpty", $rt_wrapFunction0(jl_String_isEmpty), "$toString", $rt_wrapFunction0(jl_String_toString), "$equals",
$rt_wrapFunction1(jl_String_equals), "$hashCode0", $rt_wrapFunction0(jl_String_hashCode)],
cpgmr_RegularPolygon, 0, cpgmr_RigidBody, [], 0, 3, 0, 0, ["$_init_11", $rt_wrapFunction4(cpgmr_RegularPolygon__init_), "$accept1", $rt_wrapFunction1(cpgmr_RegularPolygon_accept)],
cpgv_GameplayScreen, 0, cpgv_GameScreen, [], 0, 3, 0, 0, ["$_init_1", $rt_wrapFunction1(cpgv_GameplayScreen__init_), "$tick", $rt_wrapFunction0(cpgv_GameplayScreen_tick), "$render", $rt_wrapFunction0(cpgv_GameplayScreen_render)],
jl_NegativeArraySizeException, 0, jl_RuntimeException, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_NegativeArraySizeException__init_)],
ju_Map$Entry, 0, jl_Object, [], 3, 3, 0, 0, 0,
cpgm_GameWorld, 0, jl_Object, [], 0, 3, 0, cpgm_GameWorld_$callClinit, ["$_init_", $rt_wrapFunction0(cpgm_GameWorld__init_), "$addDynamicObject", $rt_wrapFunction1(cpgm_GameWorld_addDynamicObject), "$addStaticObject", $rt_wrapFunction1(cpgm_GameWorld_addStaticObject), "$getDynamicObjects", $rt_wrapFunction0(cpgm_GameWorld_getDynamicObjects), "$getStaticObjects", $rt_wrapFunction0(cpgm_GameWorld_getStaticObjects)],
jl_IncompatibleClassChangeError, 0, jl_LinkageError, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_IncompatibleClassChangeError__init_)],
cpgci_InputController, 0, jl_Object, [], 0, 3, 0, 0, ["$_init_21", $rt_wrapFunction2(cpgci_InputController__init_), "$getMouse", $rt_wrapFunction0(cpgci_InputController_getMouse), "$getMousePos", $rt_wrapFunction0(cpgci_InputController_getMousePos)],
cpgmc_CollisionResponse2D, 0, cpgmcr_CollisionResponse, [], 0, 3, 0, 0, ["$_init_18", $rt_wrapFunction3(cpgmc_CollisionResponse2D__init_0), "$handleResponse", $rt_wrapFunction0(cpgmc_CollisionResponse2D_handleResponse)],
ju_Iterator, 0, jl_Object, [], 3, 3, 0, 0, 0,
ju_AbstractList$1, 0, jl_Object, [ju_Iterator], 0, 0, 0, 0, ["$_init_19", $rt_wrapFunction1(ju_AbstractList$1__init_), "$hasNext", $rt_wrapFunction0(ju_AbstractList$1_hasNext), "$next", $rt_wrapFunction0(ju_AbstractList$1_next)],
cpgmc_CollisionManager$_init_$lambda$_0_3, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_3__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_3_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_3_accept)],
cpgmc_CollisionManager$_init_$lambda$_0_4, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_4__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_4_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_4_accept)],
cpgmc_CollisionManager$_init_$lambda$_0_5, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_5__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_5_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_5_accept)],
cpgmc_CollisionManager$_init_$lambda$_0_6, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_6__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_6_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_6_accept)],
jlr_Array, 0, jl_Object, [], 4, 3, 0, 0, 0,
cpgmc_CollisionManager$_init_$lambda$_0_0, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_0__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_0_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_0_accept)],
cpgmc_CollisionManager$_init_$lambda$_0_1, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_1__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_1_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_1_accept)],
cpgu_VMath, 0, jl_Object, [], 4, 3, 0, 0, 0,
cpgmc_CollisionManager$_init_$lambda$_0_2, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_2__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_2_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_2_accept)],
jl_NullPointerException, 0, jl_RuntimeException, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_NullPointerException__init_)],
cpgci_Mouse$1, 0, jl_Object, [], 32, 0, 0, cpgci_Mouse$1_$callClinit, 0,
otci_IntegerUtil, 0, jl_Object, [], 4, 3, 0, 0, 0,
juf_Consumer, 0, jl_Object, [], 3, 3, 0, 0, 0,
cpgv_GameplayScreen$render$lambda$_2_0, 0, jl_Object, [juf_Consumer], 0, 3, 0, 0, ["$_init_22", $rt_wrapFunction1(cpgv_GameplayScreen$render$lambda$_2_0__init_), "$accept0", $rt_wrapFunction1(cpgv_GameplayScreen$render$lambda$_2_0_accept0), "$accept3", $rt_wrapFunction1(cpgv_GameplayScreen$render$lambda$_2_0_accept)],
jl_Math, 0, jl_Object, [], 4, 3, 0, 0, 0,
cpgv_GameplayScreen$render$lambda$_2_1, 0, jl_Object, [juf_Consumer], 0, 3, 0, 0, ["$_init_22", $rt_wrapFunction1(cpgv_GameplayScreen$render$lambda$_2_1__init_), "$accept0", $rt_wrapFunction1(cpgv_GameplayScreen$render$lambda$_2_1_accept0), "$accept3", $rt_wrapFunction1(cpgv_GameplayScreen$render$lambda$_2_1_accept)],
cpgmc_CollisionManager$_init_$lambda$_0_7, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_7__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_7_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_7_accept)],
cpgmc_CollisionManager$_init_$lambda$_0_8, 0, jl_Object, [juf_BiConsumer], 0, 3, 0, 0, ["$_init_15", $rt_wrapFunction1(cpgmc_CollisionManager$_init_$lambda$_0_8__init_), "$accept", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_8_accept0), "$accept2", $rt_wrapFunction2(cpgmc_CollisionManager$_init_$lambda$_0_8_accept)],
cpgmc_CollisionDetection, 0, jl_Object, [], 4, 3, 0, 0, 0,
otji_JS, 0, jl_Object, [], 4, 3, 0, 0, 0,
cpgc_GameRoot$start$lambda$_4_0, 0, jl_Object, [otjb_TimerHandler], 0, 3, 0, 0, ["$_init_2", $rt_wrapFunction1(cpgc_GameRoot$start$lambda$_4_0__init_), "$onTimer", $rt_wrapFunction0(cpgc_GameRoot$start$lambda$_4_0_onTimer)],
ju_Objects, 0, jl_Object, [], 4, 3, 0, 0, 0,
ju_MapEntry, 0, jl_Object, [ju_Map$Entry, jl_Cloneable], 0, 0, 0, 0, ["$_init_26", $rt_wrapFunction2(ju_MapEntry__init_)],
ju_HashMap$HashEntry, 0, ju_MapEntry, [], 0, 0, 0, 0, ["$_init_29", $rt_wrapFunction2(ju_HashMap$HashEntry__init_)],
jlr_Type, 0, jl_Object, [], 3, 3, 0, 0, 0,
ju_AbstractSet, 0, ju_AbstractCollection, [ju_Set], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_AbstractSet__init_)],
cpgc_GameRoot, 0, jl_Object, [], 4, 3, 0, 0, ["$_init_", $rt_wrapFunction0(cpgc_GameRoot__init_), "$start", $rt_wrapFunction0(cpgc_GameRoot_start), "$stop", $rt_wrapFunction0(cpgc_GameRoot_stop)],
ju_AbstractMap, 0, jl_Object, [ju_Map], 1, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_AbstractMap__init_)],
ju_HashMap, 0, ju_AbstractMap, [jl_Cloneable, ji_Serializable], 0, 3, 0, 0, ["$newElementArray", $rt_wrapFunction1(ju_HashMap_newElementArray), "$_init_", $rt_wrapFunction0(ju_HashMap__init_1), "$_init_8", $rt_wrapFunction1(ju_HashMap__init_), "$_init_28", $rt_wrapFunction2(ju_HashMap__init_0), "$containsKey", $rt_wrapFunction1(ju_HashMap_containsKey), "$get", $rt_wrapFunction1(ju_HashMap_get), "$entryByKey", $rt_wrapFunction1(ju_HashMap_entryByKey), "$findNonNullKeyEntry", $rt_wrapFunction3(ju_HashMap_findNonNullKeyEntry),
"$findNullKeyEntry", $rt_wrapFunction0(ju_HashMap_findNullKeyEntry), "$put", $rt_wrapFunction2(ju_HashMap_put), "$rehash0", $rt_wrapFunction1(ju_HashMap_rehash), "$rehash", $rt_wrapFunction0(ju_HashMap_rehash0), "$remove", $rt_wrapFunction1(ju_HashMap_remove), "$removeByKey", $rt_wrapFunction1(ju_HashMap_removeByKey)]]);
$rt_metadata([otji_JSWrapper, 0, jl_Object, [], 4, 3, 0, 0, 0,
ju_HashSet, 0, ju_AbstractSet, [jl_Cloneable, ji_Serializable], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(ju_HashSet__init_0), "$_init_30", $rt_wrapFunction1(ju_HashSet__init_), "$add0", $rt_wrapFunction1(ju_HashSet_add), "$contains", $rt_wrapFunction1(ju_HashSet_contains), "$remove0", $rt_wrapFunction1(ju_HashSet_remove)],
otp_Platform, 0, jl_Object, [], 4, 3, 0, 0, 0,
cpgc_GameplayController$update$lambda$_1_0, 0, jl_Object, [juf_Consumer], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(cpgc_GameplayController$update$lambda$_1_0__init_), "$accept0", $rt_wrapFunction1(cpgc_GameplayController$update$lambda$_1_0_accept0), "$accept3", $rt_wrapFunction1(cpgc_GameplayController$update$lambda$_1_0_accept)],
jl_IllegalArgumentException, 0, jl_RuntimeException, [], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_IllegalArgumentException__init_1), "$_init_1", $rt_wrapFunction1(jl_IllegalArgumentException__init_2)],
cpgmc_CollisionManager$handleCollisions$lambda$_3_0, 0, jl_Object, [juf_Consumer], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(cpgmc_CollisionManager$handleCollisions$lambda$_3_0__init_), "$accept0", $rt_wrapFunction1(cpgmc_CollisionManager$handleCollisions$lambda$_3_0_accept0), "$accept4", $rt_wrapFunction1(cpgmc_CollisionManager$handleCollisions$lambda$_3_0_accept)],
cpgmc_Manifold, 0, jl_Object, [], 4, 3, 0, 0, ["$_init_24", $rt_wrapFunction3(cpgmc_Manifold__init_1), "$_init_25", $rt_wrapFunction4(cpgmc_Manifold__init_0), "$getNormal", $rt_wrapFunction0(cpgmc_Manifold_getNormal), "$getPoint", $rt_wrapFunction0(cpgmc_Manifold_getPoint), "$getPoint2", $rt_wrapFunction0(cpgmc_Manifold_getPoint2), "$getPenetration", $rt_wrapFunction0(cpgmc_Manifold_getPenetration)],
cpgmp_VerletObject, 0, cpgm_GameObject, [], 1, 3, 0, 0, 0,
cpgci_Mouse, 0, jl_Object, [], 0, 3, 0, 0, ["$_init_5", $rt_wrapFunction2(cpgci_Mouse__init_), "$getPos", $rt_wrapFunction0(cpgci_Mouse_getPos), "$isButtonDown", $rt_wrapFunction1(cpgci_Mouse_isButtonDown), "$addListenersCanvas", $rt_wrapFunction1(cpgci_Mouse_addListenersCanvas)],
jl_Class, 0, jl_Object, [jlr_AnnotatedElement, jlr_Type], 4, 3, 0, 0, ["$getPlatformClass", $rt_wrapFunction0(jl_Class_getPlatformClass), "$getName", $rt_wrapFunction0(jl_Class_getName), "$getComponentType", $rt_wrapFunction0(jl_Class_getComponentType)],
ju_Comparator, 0, jl_Object, [], 3, 3, 0, 0, 0,
jl_String$_clinit_$lambda$_115_0, 0, jl_Object, [ju_Comparator], 0, 3, 0, 0, ["$_init_", $rt_wrapFunction0(jl_String$_clinit_$lambda$_115_0__init_)]]);
let $rt_booleanArrayCls = $rt_arraycls($rt_booleancls),
$rt_charArrayCls = $rt_arraycls($rt_charcls),
$rt_intArrayCls = $rt_arraycls($rt_intcls);
$rt_stringPool(["A rigid body must have 3 or more vertices", "A rigid body must be convex", "RigidBody", "null", "MiddleClick", "LeftClick", "RightClick", "SideButton1", "SideButton2", "black", "RigidBody-RigidBody", "RigidBody-RigidCircle", "RigidBody-VerletObject", "RigidCircle-RigidBody", "RigidCircle-RigidCircle", "RigidCircle-VerletObject", "VerletObject-RigidBody", "VerletObject-RigidCircle", "VerletObject-VerletObject", "radius must be positive", "RigidCircle", "Empty vector array", "0", "GameplayScreen"]);
jl_String.prototype.toString = function() {
    return $rt_ustr(this);
};
jl_String.prototype.valueOf = jl_String.prototype.toString;
jl_Object.prototype.toString = function() {
    return $rt_ustr(jl_Object_toString(this));
};
jl_Object.prototype.__teavm_class__ = function() {
    return $dbg_class(this);
};
let $rt_export_main = $rt_mainStarter(cpg_Launcher_main);
$rt_export_main.javaException = $rt_javaException;
let $rt_jso_marker = Symbol('jsoClass');
(() => {
    let c;
    c = cpg_Launcher$main$lambda$_1_0.prototype;
    c.handleEvent = $rt_callWithReceiver(cpg_Launcher$main$lambda$_1_0_handleEvent$exported$0);
    c = cpgci_Mouse$addListenersCanvas$lambda$_12_1.prototype;
    c.handleEvent = $rt_callWithReceiver(cpgci_Mouse$addListenersCanvas$lambda$_12_1_handleEvent$exported$0);
    c = cpgci_Mouse$addListenersCanvas$lambda$_12_2.prototype;
    c.handleEvent = $rt_callWithReceiver(cpgci_Mouse$addListenersCanvas$lambda$_12_2_handleEvent$exported$0);
    c = cpgci_Mouse$addListenersCanvas$lambda$_12_0.prototype;
    c.handleEvent = $rt_callWithReceiver(cpgci_Mouse$addListenersCanvas$lambda$_12_0_handleEvent$exported$0);
    c = cpgc_GameRoot$start$lambda$_4_0.prototype;
    c.onTimer = $rt_callWithReceiver(cpgc_GameRoot$start$lambda$_4_0_onTimer$exported$0);
})();
$rt_exports.main = $rt_export_main;
}));

//# sourceMappingURL=classes.js.map