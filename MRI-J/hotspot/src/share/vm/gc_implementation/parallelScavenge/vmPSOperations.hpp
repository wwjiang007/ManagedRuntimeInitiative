/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *   
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *   
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *  
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *   
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *  
 */
// This file is a derivative work resulting from (and including) modifications
// made by Azul Systems, Inc.  The date of such changes is 2010.
// Copyright 2010 Azul Systems, Inc.  All Rights Reserved.
//
// Please contact Azul Systems, Inc., 1600 Plymouth Street, Mountain View, 
// CA 94043 USA, or visit www.azulsystems.com if you need additional information 
// or have any questions.
#ifndef VMPSOPERATIONS_HPP
#define VMPSOPERATIONS_HPP

#include "vmGCOperations.hpp"

class VM_ParallelGCFailedAllocation: public VM_GC_Operation {
 private:
  size_t    _size;
  bool      _is_tlab;
  HeapWord* _result;

 public:
  VM_ParallelGCFailedAllocation(size_t size, bool is_tlab,
                                unsigned int gc_count);

  virtual VMOp_Type type() const {
    return VMOp_ParallelGCFailedAllocation;
  }
  virtual void doit();
  
  HeapWord* result() const       { return _result; }
};

class VM_ParallelGCFailedPermanentAllocation: public VM_GC_Operation {
private:
  size_t    _size;
  HeapWord* _result;

 public:
  VM_ParallelGCFailedPermanentAllocation(size_t size,
                                         unsigned int gc_count,
                                         unsigned int full_gc_count);
  virtual VMOp_Type type() const { 
    return VMOp_ParallelGCFailedPermanentAllocation; 
  }
  virtual void doit();
  HeapWord* result() const       { return _result; }
};

class VM_ParallelGCSystemGC: public VM_GC_Operation {
 public:
  VM_ParallelGCSystemGC(unsigned int gc_count, unsigned int full_gc_count,
                        GCCause::Cause gc_cause);
  virtual VMOp_Type type() const { return VMOp_ParallelGCSystemGC; }
  virtual void doit();
};
#endif // VMPSOPERATIONS_HPP
